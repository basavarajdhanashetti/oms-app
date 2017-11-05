package com.bsd.oms.controllers;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.bsd.oms.entity.Department;
import com.bsd.oms.entity.User;
import com.bsd.oms.process.Product;
import com.bsd.oms.process.ProductCategory;
import com.bsd.oms.process.PurchaseItem;
import com.bsd.oms.process.PurchaseRequest;

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
		model.addAttribute("categoryList", getCategories());
		model.addAttribute("departmentList", getDepartments());
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
		model.addAttribute("categoryList", getCategories());
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
		model.addAttribute("categoryList", getCategories());
		model.addAttribute("departmentList", getDepartments());
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
		item.setProduct(getProduct(item.getCategory(), item.getSubCategory(), item.getProductId()));
		req.getItems().add(item);
		model.addAttribute("purchaseReqForm", req);
		session.setAttribute("Session_purchaseRequest", req);
		model.addAttribute("categoryList", getCategories());
		model.addAttribute("departmentList", getDepartments());
		return "purchaseReqForm";
	}
	
	/**
	 * 
	 * @return
	 */
	private List<Department> getDepartments() {
		ResponseEntity<Department[]> categoryResp = restTemplate.getForEntity(this.omsRestRootURL + "/departments", Department[].class);

		if (categoryResp.getStatusCode() == HttpStatus.OK) {
			return Arrays.asList(categoryResp.getBody());
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @return
	 * @return
	 */
	private List<ProductCategory> getCategories() {
		ResponseEntity<ProductCategory[]> categoryResp = restTemplate.getForEntity(this.omsRestRootURL + "/categories",
				ProductCategory[].class);

		if (categoryResp.getStatusCode() == HttpStatus.OK) {
			return Arrays.asList(categoryResp.getBody());
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @return
	 * @return
	 */
	private Product getProduct(long categoryId, long subCategoryId, long productId) {
		ResponseEntity<Product> categoryResp = restTemplate.getForEntity(this.omsRestRootURL + "/categories/" + categoryId
				+ "/subcategories/" + subCategoryId + "/products/" + productId, Product.class);

		if (categoryResp.getStatusCode() == HttpStatus.OK) {
			return categoryResp.getBody();
		} else {
			return null;
		}
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
		req.setRequestNo("PR-001");

		PurchaseItem item = new PurchaseItem();
		item.setCategory(1);
		item.setDescription("Papers");
		item.setId(1);
		item.setProductId(1);
		item.setQuantity(10);
		item.setSubCategory(1);
		item.setProduct(getProduct(1, 1, 1));

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
	@RequestMapping(method = RequestMethod.GET, path = "/purchases/{taskId}")
	public String getPurchaseRequest(Model model, @PathVariable long taskId, HttpSession session) {
		System.out.println("Getting PurchaseRequest details");

		User user = (User) session.getAttribute("Session_UserDetails");
		if(user == null){
			return "rediect:/";
		}
		
		ResponseEntity<PurchaseRequest> purchaseRequestResp = restTemplate.getForEntity(this.omsProcessRootURL + "/users/"+user.getUserName()+"/purchase-requests/" + taskId,
				PurchaseRequest.class);

		if (purchaseRequestResp.getStatusCode() == HttpStatus.OK) {
			PurchaseRequest purchaseRequest = purchaseRequestResp.getBody();
			purchaseRequest.setId(taskId);

			session.setAttribute("Session_purchaseRequest", purchaseRequest);
			model.addAttribute("purchaseReqForm", purchaseRequest);
			model.addAttribute("categoryList", getCategories());
			model.addAttribute("departmentList", getDepartments());
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
			return "rediect:/";
		}
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
			return "rediect:/";
		}

		Map<String, Object> taskData = new HashMap<String, Object>();
		taskData.put("prDetailsOut", purchaseRequest);

		ResponseEntity<Message> processResponse = restTemplate.postForEntity(this.omsRestRootURL + "/users/" + user.getUserName()
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
