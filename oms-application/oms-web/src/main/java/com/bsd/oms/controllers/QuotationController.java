package com.bsd.oms.controllers;

import java.util.ArrayList;
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
import com.bsd.oms.dto.FormSelection;
import com.bsd.oms.entity.QuotationItem;
import com.bsd.oms.entity.User;
import com.bsd.oms.process.ApprovalDetails;
import com.bsd.oms.process.PRQuotations;
import com.bsd.oms.process.PurchaseItem;
import com.bsd.oms.process.PurchaseRequest;
import com.bsd.oms.process.Quotation;
import com.bsd.oms.process.TaskSummary;
import com.bsd.oms.service.EntityService;
import com.bsd.oms.utils.OMSDateUtil;
import com.bsd.oms.utils.TaskEnum;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Basavaraj Dhanashetti
 *
 */
@Controller
public class QuotationController {

	private static final Logger LOG = LoggerFactory.getLogger(QuotationController.class);

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
	@RequestMapping(method = RequestMethod.GET, path = "/quotations/call/tasks/{taskId}")
	public String getCallForQuotationPage(Model model, @PathVariable long taskId, HttpSession session) {
		System.out.println("Getting CallForQuotationPage details");

		User user = (User) session.getAttribute("Session_UserDetails");
		if (user == null) {
			return "redirect:/logout";
		}

		ResponseEntity<Map> purchaseRequestResp = restTemplate.getForEntity(this.omsProcessRootURL + "/users/" + user.getUserName()
				+ "/tasks/" + taskId + "/content", Map.class);

		if (purchaseRequestResp.getStatusCode() == HttpStatus.OK) {

			ObjectMapper objMapper = new ObjectMapper();
			PurchaseRequest purchaseRequest = objMapper.convertValue(purchaseRequestResp.getBody().get("purchaseRequestIN"),
					PurchaseRequest.class);

			for (PurchaseItem item : purchaseRequest.getItems()) {
				item.setProduct(entityService.getProduct(item.getCategory(), item.getSubCategory(), item.getProductId()));
			}

			ResponseEntity<TaskSummary> taskSummaryResp = restTemplate.getForEntity(this.omsProcessRootURL + "/users/" + user.getUserName()
					+ "/tasks/" + taskId + "", TaskSummary.class);
			if (taskSummaryResp.getStatusCode() == HttpStatus.OK) {
				model.addAttribute("taskSummary", taskSummaryResp.getBody());
			} else {
				LOG.error("Error fetching task summary data for task id: {0}", taskId);
			}
			session.setAttribute("Session_purchaseRequest", purchaseRequest);
			model.addAttribute("purchaseReqForm", purchaseRequest);
			model.addAttribute("prQuotationForm", new PRQuotations());
			model.addAttribute("department", entityService.getDepartmentById(Long.parseLong(purchaseRequest.getDepartment())));
			model.addAttribute("vendorList", entityService.getVendors());
		} else {
			model.addAttribute("message", "Problem fetching tasks for you. Please contact Admin.");
		}
		return "callForQuotationsForm";
	}

	/**
	 * 
	 * @param model
	 * @param purchaseRequest
	 * @return
	 */
	@RequestMapping(path = "quotations/call/complete", method = RequestMethod.POST)
	public String completePRTask(@ModelAttribute PRQuotations prQuotations, Model model, HttpSession session,
			@RequestParam(name = "taskId") long taskId) {
		System.out.println("Request for prQuotations with details: " + prQuotations.toString());

		User user = (User) session.getAttribute("Session_UserDetails");
		if (user == null) {
			return "redirect:/logout";
		}
		PurchaseRequest purchaseRequest = (PurchaseRequest) session.getAttribute("Session_purchaseRequest");
		prQuotations.setQuotations(new ArrayList<Quotation>());
		for (long vendorId : prQuotations.getVendorIds()) {
			Quotation quotation = new Quotation();
			quotation.setVendor(entityService.getVendor(vendorId));
			quotation.setItems(purchaseRequest.getItems());
			prQuotations.getQuotations().add(quotation);
		}

		Map<String, Object> taskData = new HashMap<String, Object>();
		taskData.put("prQuotationsOUT", prQuotations);

		ResponseEntity<Message> processResponse = restTemplate.postForEntity(this.omsProcessRootURL + "/users/" + user.getUserName()
				+ "/tasks/" + taskId + "/complete", taskData, Message.class);

		if (processResponse.getStatusCode() == HttpStatus.OK) {
			LOG.debug("Task completed now.");
			model.addAttribute("message", "Task completed successfully (" + processResponse.getBody().getMessage() + ")");
		} else {
			model.addAttribute("message", "Problem completing task. Please contact Admin.");
		}
		return "redirect:/process/tasks";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/quotations/tasks/{taskId}")
	public String getQuotationPage(Model model, @PathVariable long taskId, HttpSession session) {
		System.out.println("Getting CallForQuotationPage details");

		User user = (User) session.getAttribute("Session_UserDetails");
		if (user == null) {
			return "redirect:/logout";
		}

		TaskSummary taskSummary = null;
		PRQuotations prQuotations = null;
		PurchaseRequest purchaseRequest = null;

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

		if (taskSummary.getName().equals(TaskEnum.ApprovePurchaseRequest.getTaskName())
				|| taskSummary.getName().equals(TaskEnum.QuotationSubmission.getTaskName())) {
			if (purchaseRequestResp.getStatusCode() == HttpStatus.OK) {
				ObjectMapper objMapper = new ObjectMapper();
				prQuotations = objMapper.convertValue(purchaseRequestResp.getBody().get("prQuotationsIN"), PRQuotations.class);
				purchaseRequest = objMapper.convertValue(purchaseRequestResp.getBody().get("purchaseRequestIN"), PurchaseRequest.class);
			}

			if (prQuotations == null || purchaseRequest == null) {
				model.addAttribute("message", "Problem fetching tasks for you. Please contact Admin.");
				return "redirect:/process/tasks";
			} else {
				session.setAttribute("Session_purchaseRequest", purchaseRequest);
				session.setAttribute("Session_prQuotations", prQuotations);
				model.addAttribute("department", entityService.getDepartmentById(Long.parseLong(purchaseRequest.getDepartment())));
				model.addAttribute("purchaseReqForm", purchaseRequest);
				model.addAttribute("prQuotationForm", prQuotations);
			}
			return "quotationsForm";

		} else if (taskSummary.getName().equals(TaskEnum.QuotationSelection.getTaskName())
				|| taskSummary.getName().equals(TaskEnum.AmendQuotation.getTaskName())
				|| taskSummary.getName().equals(TaskEnum.QuotationApproval.getTaskName())) {
			FormSelection formSelection = new FormSelection();
			if (purchaseRequestResp.getStatusCode() == HttpStatus.OK) {
				ObjectMapper objMapper = new ObjectMapper();
				purchaseRequest = objMapper.convertValue(purchaseRequestResp.getBody().get("purchaseRequestIN"), PurchaseRequest.class);

				if (taskSummary.getName().equals(TaskEnum.QuotationApproval.getTaskName())) {
					long selectedId = (Integer) purchaseRequestResp.getBody().get("selectedQuotIdIN");
					formSelection.setId(selectedId);
				}
			}
			List<com.bsd.oms.entity.Quotation> quotations = entityService.getQuotationsByPurchaseRequest(purchaseRequest.getId());

			com.bsd.oms.entity.PurchaseRequest prEntity = quotations.get(0).getPurchaseRequest();
			model.addAttribute("purchaseReqForm", prEntity);
			model.addAttribute("quotations", quotations);
			model.addAttribute("quotationSelectionForm", formSelection);
			return "quotationApprovalForm";
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
	@RequestMapping(path = "/quotations/view/{id}", method = RequestMethod.GET)
	public String viewQuotationItem(@PathVariable long id, Model model, HttpSession session) {
		System.out.println("Getting Quotation Details for id:" + id);
		com.bsd.oms.entity.Quotation quotation = entityService.getQuotation(id);
		com.bsd.oms.entity.PurchaseRequest prEntity = quotation.getPurchaseRequest();
		model.addAttribute("purchaseReqForm", prEntity);
		model.addAttribute("quotationForm", quotation);
		model.addAttribute("taskSummary", session.getAttribute("Session_taskSummary"));

		return "viewQuotationItem";
	}

	/**
	 * 
	 * @param model
	 * @param purchaseRequest
	 * @return
	 */
	@RequestMapping(path = "/quotations/add/{rowInd}", method = RequestMethod.GET)
	public String getQuotationItem(@PathVariable int rowInd, Model model, HttpSession session) {
		PRQuotations prQuotations = (PRQuotations) session.getAttribute("Session_prQuotations");
		model.addAttribute("vendorQuotForm", prQuotations.getQuotations().get(rowInd));
		model.addAttribute("rowInd", rowInd);
		return "vendorQuotationForm";
	}

	/**
	 * 
	 * @param model
	 * @param purchaseRequest
	 * @return
	 */
	@RequestMapping(path = "/quotations/add/{rowInd}", method = RequestMethod.POST)
	public String addQuotationItem(@ModelAttribute Quotation quotation, @PathVariable int rowInd, Model model, HttpSession session) {
		PRQuotations prQuotations = (PRQuotations) session.getAttribute("Session_prQuotations");

		Quotation tmpQuot = prQuotations.getQuotations().get(rowInd);
		quotation.setVendor(tmpQuot.getVendor());

		for (PurchaseItem item : quotation.getItems()) {
			item.setProduct(entityService.getProduct(item.getCategory(), item.getSubCategory(), item.getProductId()));
		}

		prQuotations.getQuotations().set(rowInd, quotation);

		PurchaseRequest purchaseRequest = (PurchaseRequest) session.getAttribute("Session_purchaseRequest");
		session.setAttribute("Session_prQuotations", prQuotations);
		model.addAttribute("purchaseReqForm", purchaseRequest);
		model.addAttribute("taskSummary", session.getAttribute("Session_taskSummary"));
		model.addAttribute("prQuotationForm", prQuotations);
		model.addAttribute("department", entityService.getDepartmentById(Long.parseLong(purchaseRequest.getDepartment())));

		return "quotationsForm";
	}

	/**
	 * 
	 * @param model
	 * @param purchaseRequest
	 * @return
	 */
	@RequestMapping(path = "/quotations/update", method = RequestMethod.POST)
	public String updateQuotationItem(@ModelAttribute com.bsd.oms.entity.Quotation quotation, Model model,
			HttpSession session) {

		com.bsd.oms.entity.Quotation dbQuo = entityService.getQuotation(quotation.getId());
		dbQuo.setQuoteAmount(quotation.getQuoteAmount());
		dbQuo.setTax(quotation.getTax());
		dbQuo.setTotalAmount(quotation.getTotalAmount()); 
		for (QuotationItem item : dbQuo.getItems()) {
			
			for (QuotationItem latestItem : quotation.getItems()) {
				
				if(item.getId() == latestItem.getId()){
					item.setUnitPrice(latestItem.getUnitPrice());
					item.setDiscount(latestItem.getDiscount());
					item.setSalePrice(latestItem.getSalePrice());
					break;
				}
			} 
		} 
		entityService.updateQuotation(dbQuo);
		TaskSummary taskSummary = (TaskSummary)session.getAttribute("Session_taskSummary");
		return "redirect:/quotations/tasks/"+taskSummary.getId();
	}

	/**
	 * 
	 * @param model
	 * @param purchaseRequest
	 * @return
	 */
	@RequestMapping(path = "/quotations/back", method = RequestMethod.GET)
	public String backQuotations(Model model, HttpSession session) {
		TaskSummary taskSummary = (TaskSummary) session.getAttribute("Session_taskSummary");
		
		if(taskSummary.getName().equals(TaskEnum.QuotationSelection.getTaskName())
				|| taskSummary.getName().equals(TaskEnum.AmendQuotation.getTaskName())
				|| taskSummary.getName().equals(TaskEnum.QuotationApproval.getTaskName())){
			
			return "redirect:/quotations/tasks/"+taskSummary.getId();
		}
		
		PurchaseRequest purchaseRequest = (PurchaseRequest) session.getAttribute("Session_purchaseRequest");
		model.addAttribute("purchaseReqForm", purchaseRequest);
		model.addAttribute("taskSummary", taskSummary);
		model.addAttribute("prQuotationForm", session.getAttribute("Session_prQuotations"));
		model.addAttribute("department", entityService.getDepartmentById(Long.parseLong(purchaseRequest.getDepartment())));

		return "quotationsForm";
	}

	/**
	 * 
	 * @param model
	 * @param purchaseRequest
	 * @return
	 */
	@RequestMapping(path = "quotations/complete", method = RequestMethod.POST)
	public String completeQuotationTask(@ModelAttribute FormSelection formSelection, Model model, HttpSession session,
			@RequestParam(name = "taskId") long taskId, @RequestParam(name = "amendreq", required = false) boolean amendreq,
			@RequestParam(name = "approved", required = false) boolean approved) {

		User user = (User) session.getAttribute("Session_UserDetails");
		if (user == null) {
			return "redirect:/logout";
		}

		TaskSummary taskSummary = (TaskSummary) session.getAttribute("Session_taskSummary");

		Map<String, Object> taskData = new HashMap<String, Object>();
		if (taskSummary.getName().equals(TaskEnum.ApprovePurchaseRequest.getTaskName())
				|| taskSummary.getName().equals(TaskEnum.QuotationSubmission.getTaskName())) {
			PRQuotations prQuotations = (PRQuotations) session.getAttribute("Session_prQuotations");
			System.out.println("Complete quotation with details: " + prQuotations.toString());
			prQuotations.setApprover(new ApprovalDetails(user.getUserName(), OMSDateUtil.getCurrentDate()));
			taskData.put("prQuotationsOUT", prQuotations);
		} else if (taskSummary.getName().equals(TaskEnum.QuotationSelection.getTaskName())) {

			if (amendreq) {
				taskData.put("quotAmendOUT", amendreq);
			} else {
				taskData.put("selectedQuotIdOUT", formSelection.getId());
				taskData.put("quotAmendOUT", amendreq);
			}
		} else if (taskSummary.getName().equals(TaskEnum.QuotationApproval.getTaskName())) {
			taskData.put("quotApprovedOUT", approved);
			taskData.put("quotApprovedByOUT", user.getUserName());
		}

		ResponseEntity<Message> processResponse = restTemplate.postForEntity(this.omsProcessRootURL + "/users/" + user.getUserName()
				+ "/tasks/" + taskId + "/complete", taskData, Message.class);

		if (processResponse.getStatusCode() == HttpStatus.OK) {
			LOG.debug("Task completed now.");
			model.addAttribute("message", "Task completed successfully (" + processResponse.getBody().getMessage() + ")");
		} else {
			model.addAttribute("message", "Problem completing task. Please contact Admin.");
		}
		return "redirect:/process/tasks";
	}

	/**
	 * 
	 * @param model
	 * @param purchaseRequest
	 * @return
	 */
	@RequestMapping(path = "quotations/save", method = RequestMethod.POST)
	public String saveQuotationTaskContent(Model model, HttpSession session, @RequestParam(name = "taskId") long taskId) {

		User user = (User) session.getAttribute("Session_UserDetails");
		if (user == null) {
			return "redirect:/logout";
		}
		PRQuotations prQuotations = (PRQuotations) session.getAttribute("Session_prQuotations");
		System.out.println("Complete quotation with details: " + prQuotations.toString());

		Map<String, Object> taskData = new HashMap<String, Object>();
		taskData.put("prQuotations", prQuotations);
		taskData.put("prDetails", session.getAttribute("Session_purchaseRequest"));

		ResponseEntity<Message> processResponse = restTemplate.postForEntity(this.omsProcessRootURL + "/users/" + user.getUserName()
				+ "/tasks/" + taskId + "/save", taskData, Message.class);

		if (processResponse.getStatusCode() == HttpStatus.OK) {
			LOG.debug("Task completed now.");
			model.addAttribute("message", "Task completed successfully (" + processResponse.getBody().getMessage() + ")");
		} else {
			model.addAttribute("message", "Problem completing task. Please contact Admin.");
		}
		return "redirect:/process/tasks";
	}
}
