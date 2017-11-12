package com.bsd.oms.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.bsd.oms.domain.Message;
import com.bsd.oms.entity.ApprovalDetails;
import com.bsd.oms.entity.ApprovalDetails.ApprovalStatusType;
import com.bsd.oms.entity.ApprovalDetails.MaterialType;
import com.bsd.oms.entity.PurchaseOrder;
import com.bsd.oms.entity.PurchaseRequest;
import com.bsd.oms.entity.User;
import com.bsd.oms.process.TaskSummary;
import com.bsd.oms.service.EntityService;
import com.bsd.oms.utils.TaskEnum;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Basavaraj Dhanashetti
 *
 */
@Controller
public class PurchaseOrderController {

	private static final Logger LOG = LoggerFactory.getLogger(PurchaseOrderController.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private EntityService entityService;

	@Value("${oms-rest-url}")
	private String omsRestRootURL;

	@Value("${oms-process-url}")
	private String omsProcessRootURL;

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/purchaseorders/tasks/{taskId}")
	public String getPurchaseOrderPage(Model model, @PathVariable long taskId, HttpSession session) {
		System.out.println("Getting CallForPurchaseOrderPage details");

		User user = (User) session.getAttribute("Session_UserDetails");
		if (user == null) {
			return "redirect:/logout";
		}

		TaskSummary taskSummary = null;

		ResponseEntity<TaskSummary> taskSummaryResp = restTemplate.getForEntity(this.omsProcessRootURL + "/users/" + user.getUserName()
				+ "/tasks/" + taskId + "", TaskSummary.class);

		if (taskSummaryResp.getStatusCode() == HttpStatus.OK) {
			taskSummary = taskSummaryResp.getBody();
			model.addAttribute("taskSummary", taskSummary);
			session.setAttribute("Session_taskSummary", taskSummaryResp.getBody());
		} else {
			LOG.error("Error fetching task summary data for task id: {0}", taskId);
			model.addAttribute("message", "Problem fetching tasks for you. Please contact Admin.");
			return "redirect:/process/tasks";
		}

		ResponseEntity<Map> purchaseRequestResp = restTemplate.getForEntity(this.omsProcessRootURL + "/users/" + user.getUserName()
				+ "/tasks/" + taskId + "/content", Map.class);

		if (taskSummary.getName().equals(TaskEnum.PurchaseOrderApproval.getTaskName())) {
			if (purchaseRequestResp.getStatusCode() == HttpStatus.OK) {
				ObjectMapper objMapper = new ObjectMapper();
				long quotationId = objMapper.convertValue(purchaseRequestResp.getBody().get("quotationIdIN"), Long.class);
				PurchaseOrder purchaseOrder = entityService.getPurchaseOrderByQuotation(quotationId);

				PurchaseRequest prEntity = purchaseOrder.getQuotation().getPurchaseRequest();
				model.addAttribute("purchaseRequest", prEntity);
				model.addAttribute("purchaseOrder", purchaseOrder);
				return "purchaseOrderForm";
			}
		}

		model.addAttribute("message", "Problem fetching tasks for you. Please contact Admin.");
		return "redirect:/process/tasks";
	}

	/**
	 * 
	 * @param model
	 * @param purchaseRequest
	 * @return
	 */
	@RequestMapping(path = "purchaseorders/complete", method = RequestMethod.POST)
	public String completePOTask(@ModelAttribute PurchaseOrder purchaseOrder, Model model, HttpSession session, @RequestParam(name = "taskId") long taskId,
			@RequestParam(name = "approved") boolean approved) {
		System.out.println("PO complete task. ");

		User user = (User) session.getAttribute("Session_UserDetails");
		if (user == null) {
			return "redirect:/logout";
		}

		Map<String, Object> taskData = new HashMap<String, Object>();
		taskData.put("poApprovedOUT", approved);
		taskData.put("purchaseOrderIdOUT", purchaseOrder.getId());
		taskData.put("poApprovedByOUT", user.getUserName());

		ResponseEntity<Message> processResponse = restTemplate.postForEntity(this.omsProcessRootURL + "/users/" + user.getUserName()
				+ "/tasks/" + taskId + "/complete", taskData, Message.class);

		if (processResponse.getStatusCode() == HttpStatus.OK) {
			LOG.debug("Task completed now.");
			entityService.postApprovalData(getApprovalDetails(user.getUserName(), purchaseOrder.getId(), MaterialType.PurchaseOrder, ApprovalStatusType.Approved));
			model.addAttribute("message", "Task completed successfully (" + processResponse.getBody().getMessage() + ")");
		} else {
			model.addAttribute("message", "Problem completing task. Please contact Admin.");
		}
		return "redirect:/process/tasks";
	}
	
	/**
	 * 
	 * @param userId
	 * @param createdDate
	 * @param referenceId
	 * @param materialType
	 * @param approvalStatusType
	 * @return
	 */
	private ApprovalDetails getApprovalDetails(String userId, long referenceId, MaterialType materialType,
			ApprovalStatusType approvalStatusType) {
		// Set Approval Details
		ApprovalDetails apprDetails = new ApprovalDetails();
		apprDetails.setUserId(userId);
		apprDetails.setCreatedDate(new Date());
		apprDetails.setMaterial(materialType);
		apprDetails.setStatus(approvalStatusType);
		apprDetails.setReferenceId(referenceId);
		return apprDetails;
	}
}
