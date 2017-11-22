package com.bsd.oms.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.bsd.oms.entity.PurchaseOrder;
import com.bsd.oms.entity.PurchaseOrderItem;
import com.bsd.oms.entity.StoreInwardItem;
import com.bsd.oms.entity.StoreInwardRegister;
import com.bsd.oms.entity.StoreInwardRegister.Status;
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
public class StoreInwardsController {

	private static final Logger LOG = LoggerFactory.getLogger(StoreInwardsController.class);

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
	@RequestMapping(method = RequestMethod.GET, path = "/stores/tasks/{taskId}")
	public String getNewRegisterPage(Model model, @PathVariable long taskId, HttpSession session) {
		LOG.debug("In getNewRegisterPage");

		User user = (User) session.getAttribute("Session_UserDetails");

		TaskSummary taskSummary = entityService.getTaskSummary(user.getUserName(), taskId);

		if (taskSummary == null) {
			LOG.error("Error fetching task summary data for task id: {0}", taskId);
			model.addAttribute("message", "Problem fetching tasks for you. Please contact Admin.");
			return "redirect:/process/tasks";
		}
		model.addAttribute("taskSummary", taskSummary);
		session.setAttribute("Session_taskSummary", taskSummary);

		Map taskContentMap = entityService.getTaskContentMap(user.getUserName(), taskId);

		if (taskSummary.getName().equals(TaskEnum.InwardMaterials.getTaskName())
				|| taskSummary.getName().equals(TaskEnum.InwardMaterialsApproval.getTaskName())) {
			LOG.info("task name:" + taskSummary.getName());
			ObjectMapper objMapper = new ObjectMapper();
			long purchaseOrderId = objMapper.convertValue(taskContentMap.get("purchaseOrderIdIN"), Long.class);
			PurchaseOrder purchaseOrder = entityService.getPurchaseOrder(purchaseOrderId);
			model.addAttribute("purchaseOrder", purchaseOrder);
			StoreInwardRegister inwardRegister = populateInwardmaterialData(purchaseOrder);
			model.addAttribute("inwardRegForm", inwardRegister);
			return "storeInwardForm";
		}

		model.addAttribute("message", "Problem fetching tasks for you. Please contact Admin.");
		return "redirect:/process/tasks";
	}

	/**
	 * 
	 * @param purchaseOrder
	 * @return
	 */
	private StoreInwardRegister populateInwardmaterialData(PurchaseOrder po) {

		StoreInwardRegister register = null;

		register = entityService.getStoreInwardRegisterByPOId(po.getId());

		if (register != null) {
			return register;
		}

		register = new StoreInwardRegister();
		register.setIdPurchaseOrder(po.getId());
		register.setPurchaseOrder(po);
		register.setItems(new ArrayList<StoreInwardItem>());

		for (PurchaseOrderItem poItem : po.getItems()) {
			StoreInwardItem inItem = new StoreInwardItem();
			inItem.setIdProduct(poItem.getIdProduct());
			inItem.setProduct(poItem.getProduct());
			inItem.setOrderedQty(poItem.getQuantity());
			register.getItems().add(inItem);
		}
		return register;
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/stores/inwards/save")
	public String saveReports(@ModelAttribute StoreInwardRegister inwardRegister, Model model, HttpSession session) {
		LOG.debug("In getMyReports ");

		User user = (User) session.getAttribute("Session_UserDetails");

		inwardRegister.setVerifiedBy(user.getUserName());
		inwardRegister.setStatus(Status.SAVED);

		ResponseEntity<StoreInwardRegister> reportResp = restTemplate.postForEntity(this.omsRestRootURL + "/stores/inwards",
				inwardRegister, StoreInwardRegister.class);

		if (reportResp.getStatusCode() == HttpStatus.CREATED) {
			model.addAttribute("message", "Data saved for later submit.");
		} else {
			model.addAttribute("message", "Problem fetching tasks for you. Please contact Admin.");
		}
		return "redirect:/process/tasks";
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/stores/inwards/complete")
	public String completeTask(@ModelAttribute StoreInwardRegister inwardRegister, Model model, HttpSession session,
			@RequestParam(required = false) boolean approved) {
		LOG.debug("In getMyReports ");

		User user = (User) session.getAttribute("Session_UserDetails");
		TaskSummary taskSummary = (TaskSummary) session.getAttribute("Session_taskSummary");

		if (taskSummary.getName().equals(TaskEnum.InwardMaterialsApproval.getTaskName())) {
			String comments = inwardRegister.getComments();
			inwardRegister = entityService.getStoreInwardRegister(inwardRegister.getId());
			inwardRegister.setStatus(approved ? Status.APPROVED : Status.REJECTED);
			inwardRegister.setApprovedBy(user.getUserName());
			inwardRegister.setComments(comments);
		} else {
			inwardRegister.setStatus(Status.PENDING_APPROVAL);
			inwardRegister.setVerifiedBy(user.getUserName());
			inwardRegister.setCreatedDate(new Date());
		}

		ResponseEntity<StoreInwardRegister> reportResp = restTemplate.postForEntity(this.omsRestRootURL + "/stores/inwards",
				inwardRegister, StoreInwardRegister.class);

		if (reportResp.getStatusCode() == HttpStatus.CREATED || reportResp.getStatusCode() == HttpStatus.OK) {

			Map<String, Object> taskData = new HashMap<String, Object>();

			if (taskSummary.getName().equals(TaskEnum.InwardMaterialsApproval.getTaskName())) {
				taskData.put("storeApprovalOUT", approved);
				taskData.put("storeInwardRegIdOUT", inwardRegister.getId());
			}

			ResponseEntity<Message> processResponse = restTemplate.postForEntity(this.omsProcessRootURL + "/users/" + user.getUserName()
					+ "/tasks/" + taskSummary.getId() + "/complete", taskData, Message.class);

			if (processResponse.getStatusCode() == HttpStatus.OK) {
				LOG.debug("Task completed now.");
				model.addAttribute("message", "Task completed successfully.");
			} else {
				model.addAttribute("message", "Problem completing tasks for you. Please contact Admin.");
			}
		} else {
			model.addAttribute("message", "Problem completing tasks for you. Please contact Admin.");
		}
		return "redirect:/process/tasks";
	}
}
