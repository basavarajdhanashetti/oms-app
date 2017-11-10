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
import com.bsd.oms.entity.User;
import com.bsd.oms.process.ApprovalDetails;
import com.bsd.oms.process.PurchaseItem;
import com.bsd.oms.process.PurchaseRequest;
import com.bsd.oms.process.TaskSummary;
import com.bsd.oms.service.EntityService;
import com.bsd.oms.utils.OMSDateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Basavaraj Dhanashetti
 *
 */
@Controller
public class PurchaseRequestController {

	private static final Logger LOG = LoggerFactory.getLogger(PurchaseRequestController.class);

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
	@RequestMapping(path="/purchases", method = RequestMethod.GET)
	public String newPurchaseRequest(Model model, HttpSession session) {
		System.out.println("Presenting Purchase Request Form.");
		PurchaseRequest req = getPRDetails();
		model.addAttribute("purchaseReqForm", req);
		session.setAttribute("Session_purchaseRequest", req);
		model.addAttribute("categoryList", entityService.getCategories());
		model.addAttribute("departmentList", entityService.getDepartments());
		return "purchaseReqForm";
	}

	/**
	 * 
	 * @param model
	 * @param purchaseRequest
	 * @return
	 */
	@RequestMapping(path = "/newpritem", method = RequestMethod.GET)
	public String newPRItem(Model model, HttpSession session) {
		model.addAttribute("categoryList", entityService.getCategories());
		model.addAttribute("purchaseItem", new PurchaseItem());
		return "addPurchaseItem";
	}

	/**
	 * 
	 * @param model
	 * @param purchaseRequest
	 * @return
	 */
	@RequestMapping(path = "/removepritem", method = RequestMethod.GET)
	public String newPRItem(Model model, HttpSession session, @RequestParam("index") int index) {
		PurchaseRequest req = (PurchaseRequest) session.getAttribute("Session_purchaseRequest");
		req.getItems().remove(index);
		model.addAttribute("purchaseReqForm", req);
		session.setAttribute("Session_purchaseRequest", req);
		model.addAttribute("categoryList", entityService.getCategories());
		model.addAttribute("departmentList", entityService.getDepartments());
		return "purchaseReqForm";
	}
	
	/**
	 * 
	 * @param model
	 * @param purchaseRequest
	 * @return
	 */
	@RequestMapping(path = "/addpritem", method = RequestMethod.POST)
	public String addPRItem(@ModelAttribute PurchaseItem item, Model model, HttpSession session) {
		PurchaseRequest req = (PurchaseRequest) session.getAttribute("Session_purchaseRequest");
		item.setProduct(entityService.getProduct(item.getCategory(), item.getSubCategory(), item.getProductId()));
		req.getItems().add(item);
		model.addAttribute("purchaseReqForm", req);
		session.setAttribute("Session_purchaseRequest", req);
		model.addAttribute("categoryList", entityService.getCategories());
		model.addAttribute("departmentList", entityService.getDepartments());
		return "purchaseReqForm";
	}
	
	


	/**
	 * 
	 * @return
	 */
	private PurchaseRequest getPRDetails() {
		PurchaseRequest req = new PurchaseRequest();
		req.setDepartment("1");
		req.setRequestDate("2017/11/01");
		req.setRequestedBy("Raj");
		req.setRequestNo("PR/2017/11/001");

		PurchaseItem item = new PurchaseItem();
		item.setCategory(1);
		item.setDescription("Papers");
		item.setId(1);
		item.setProductId(1);
		item.setQuantity(10);
		item.setSubCategory(1);
		item.setProduct(entityService.getProduct(1, 1, 1));

		List<PurchaseItem> items = new ArrayList<PurchaseItem>();
		items.add(item);
		req.setItems(items);
		return req;
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/purchases/tasks/{taskId}")
	public String getPurchaseRequest(Model model, @PathVariable long taskId, HttpSession session) {
		System.out.println("Getting PurchaseRequest details");

		User user = (User) session.getAttribute("Session_UserDetails");
		if(user == null){
			return "redirect:/logout";
		}
		
		ResponseEntity<Map> purchaseRequestResp = restTemplate.getForEntity(this.omsProcessRootURL + "/users/"+user.getUserName()+"/tasks/" + taskId+"/content",
				Map.class);

		if (purchaseRequestResp.getStatusCode() == HttpStatus.OK) {
			
			ObjectMapper objMapper = new ObjectMapper();
			PurchaseRequest purchaseRequest = objMapper.convertValue(purchaseRequestResp.getBody().get("purchaseRequestIN"),PurchaseRequest.class);
			purchaseRequest.setId(taskId);
			
			for (PurchaseItem item : purchaseRequest.getItems()) {
				item.setProduct(entityService.getProduct(item.getCategory(), item.getSubCategory(), item.getProductId()));
			}
			
			ResponseEntity<TaskSummary> taskSummaryResp = restTemplate.getForEntity(this.omsProcessRootURL + "/users/"+user.getUserName()+"/tasks/" + taskId+"",
					TaskSummary.class);
			if(taskSummaryResp.getStatusCode() == HttpStatus.OK){
				model.addAttribute("taskSummary", taskSummaryResp.getBody());
			}else{
				LOG.error("Error fetching task summary data for task id: {0}", taskId);
			}
			session.setAttribute("Session_purchaseRequest", purchaseRequest);
			model.addAttribute("purchaseReqForm", purchaseRequest);
			model.addAttribute("categoryList", entityService.getCategories());
			model.addAttribute("departmentList", entityService.getDepartments());
		} else {
			model.addAttribute("message", "Problem fetching tasks for you. Please contact Admin.");
		}
		return "purchaseReqForm";
	}

	/**
	 * 
	 * @param model
	 * @param purchaseRequest
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path="/purchases")
	public String processPurchaseRequest(@ModelAttribute PurchaseRequest purchaseRequest, Model model, HttpSession session) {
		System.out.println("Received purchaseRequest with details: " + purchaseRequest.toString());
		User user = (User) session.getAttribute("Session_UserDetails");
		if(user == null){
			return "redirect:/logout";
		}
		for (PurchaseItem item : purchaseRequest.getItems()) {
			item.setProduct(entityService.getProduct(item.getCategory(), item.getSubCategory(), item.getProductId()));
		}
		purchaseRequest.setRequestedBy(user.getUserName());
		ResponseEntity<Long> processResponse = restTemplate.postForEntity(this.omsProcessRootURL + "/users/"+user.getUserName()+"/purchase-requests", purchaseRequest,
				Long.class);

		if (processResponse.getStatusCode() == HttpStatus.CREATED) {
			String processEntityURL = processResponse.getHeaders().getFirst("location");
			LOG.debug("PurchaseRequest process URL:" + processEntityURL);
			model.addAttribute("message", "Process Initiated Successfully");
		} else {
			model.addAttribute("message", "Problem initiating order process. Please contact Admin.");
		}
		return "redirect:/process/tasks";
	}
	

	/**
	 * 
	 * @param model
	 * @param purchaseRequest
	 * @return
	 */
	@RequestMapping(path = "/purchases/complete", method = RequestMethod.POST)
	public String completePRTask(@ModelAttribute PurchaseRequest purchaseRequest, Model model, HttpSession session) {
		System.out.println("Updated purchaseRequest with details: " + purchaseRequest.toString());

		User user = (User) session.getAttribute("Session_UserDetails");
		if(user == null){
			return "redirect:/logout";
		}

		for (PurchaseItem item : purchaseRequest.getItems()) {
			item.setProduct(entityService.getProduct(item.getCategory(), item.getSubCategory(), item.getProductId()));
		}
		
		Map<String, Object> taskData = new HashMap<String, Object>();
		taskData.put("purchaseRequestOUT", purchaseRequest);

		ResponseEntity<Message> processResponse = restTemplate.postForEntity(this.omsProcessRootURL + "/users/" + user.getUserName()
				+ "/tasks/" + purchaseRequest.getId() + "/complete", taskData, Message.class);

		if (processResponse.getStatusCode() == HttpStatus.OK) {
			LOG.debug("Task completed now.");
			model.addAttribute("message", "Process Re-initiated Successfully (" + processResponse.getBody().getMessage() + ")");
		} else {
			model.addAttribute("message", "Problem initiating order process. Please contact Admin.");
		}
		return "redirect:/process/tasks";
	}

	/**
	 * 
	 * @param model
	 * @param purchaseRequest
	 * @return
	 */
	@RequestMapping(path = "/purchases/approval", method = RequestMethod.POST)
	public String approvePRTask(Model model, HttpSession session, @RequestParam(name="action") boolean action) {
		
		User user = (User) session.getAttribute("Session_UserDetails");
		if(user == null){
			return "redirect:/logout";
		}
		PurchaseRequest purchaseRequest = (PurchaseRequest)session.getAttribute("Session_purchaseRequest");
		System.out.println("Approve purchaseRequest with details: " + purchaseRequest.toString() + ", approval action :"+  ( action ? "Approved": "Rejected"));

		for (PurchaseItem item : purchaseRequest.getItems()) {
			item.setProduct(entityService.getProduct(item.getCategory(), item.getSubCategory(), item.getProductId()));
		}
		purchaseRequest.setApprover(new ApprovalDetails(user.getUserName(), OMSDateUtil.getCurrentDate()));
		Map<String, Object> taskData = new HashMap<String, Object>();
		taskData.put("purchaseRequestOUT", purchaseRequest);
		taskData.put("prApprovedOUT", action);

		ResponseEntity<Message> processResponse = restTemplate.postForEntity(this.omsProcessRootURL + "/users/" + user.getUserName()
				+ "/tasks/" + purchaseRequest.getId() + "/complete", taskData, Message.class);

		if (processResponse.getStatusCode() == HttpStatus.OK) {
			LOG.debug("Task completed now.");
			model.addAttribute("message", "Process Re-initiated Successfully (" + processResponse.getBody().getMessage() + ")");
		} else {
			model.addAttribute("message", "Problem initiating order process. Please contact Admin.");
		}
		return "redirect:/process/tasks";
	}
}
