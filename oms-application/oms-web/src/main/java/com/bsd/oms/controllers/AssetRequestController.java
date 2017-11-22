package com.bsd.oms.controllers;

import java.util.Arrays;
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
import com.bsd.oms.dto.FormSelection;
import com.bsd.oms.entity.AssetRequest;
import com.bsd.oms.entity.StockRegister;
import com.bsd.oms.entity.StoreInwardRegister.Status;
import com.bsd.oms.entity.User;
import com.bsd.oms.process.TaskSummary;
import com.bsd.oms.service.EntityService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Basavaraj Dhanashetti
 *
 */
@Controller
public class AssetRequestController {

	private static final Logger LOG = LoggerFactory.getLogger(AssetRequestController.class);

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
	@RequestMapping(method = RequestMethod.GET, path = "/requests")
	public String getMyRequests(Model model, HttpSession session) {
		LOG.debug("In getMyRequests");
		User user = (User) session.getAttribute("Session_UserDetails");
		List<AssetRequest> myRequsets = entityService.getMyAssetRequests(user.getUserName());
		model.addAttribute("myRequsets", myRequsets);
		return "myAssetsForm";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/requests/new")
	public String getNewRequest(Model model, HttpSession session) {
		LOG.debug("In getNewRequest");
		model.addAttribute("categoryList", entityService.getCategories());
		model.addAttribute("assetRequest", new AssetRequest());
		return "assetRequestForm";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/requests")
	public String getMyRequests(@ModelAttribute AssetRequest assetReq, Model model, HttpSession session) {
		LOG.debug("In getMyRequests");
		User user = (User) session.getAttribute("Session_UserDetails");

		assetReq.setRequestDate(new Date());
		assetReq.setUserId(user.getUserName());
		assetReq.setStatus(Status.PENDING_APPROVAL);
		assetReq.setProduct(null);
		try {
			ResponseEntity<AssetRequest> itemResp = restTemplate.postForEntity(this.omsRestRootURL + "/assetrequests", assetReq,
					AssetRequest.class);
			if (itemResp.getStatusCode() == HttpStatus.CREATED) {
				String assetURL = itemResp.getHeaders().getFirst("location");
				LOG.debug("Asset Request process URL:" + assetURL);

				AssetRequest astReq = entityService.getByURL(assetURL, AssetRequest.class);

				ResponseEntity<Long> processResponse = restTemplate.postForEntity(this.omsProcessRootURL + "/users/" + user.getUserName()
						+ "/asset-requests/" + astReq.getId(), astReq, Long.class);

				if (processResponse.getStatusCode() == HttpStatus.CREATED) {
					String processEntityURL = processResponse.getHeaders().getFirst("location");
					LOG.debug("Asset Request process URL:" + processEntityURL);
					model.addAttribute("message", "Process Initiated Successfully");
				} else {
					model.addAttribute("message", "Problem initiating order process. Please contact Admin.");
				}

			} else {
				return null;
			}
		} catch (Exception exp) {
			LOG.error("Got some exception", exp);
			return null;
		}

		return "redirect:/requests";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/requests/{id}/details")
	public String getDetails(@PathVariable long id, Model model, HttpSession session) {
		LOG.debug("In getNewRequest");
		AssetRequest astReq = entityService.getByURL(this.omsRestRootURL + "/assetrequests/" + id, AssetRequest.class);
		model.addAttribute("categoryList", entityService.getCategories());
		model.addAttribute("assetRequest", astReq);
		model.addAttribute("viewonly", true);
		return "assetRequestForm";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/requests/tasks/{taskId}")
	public String getRequestDetails(Model model, @PathVariable long taskId, HttpSession session) {
		LOG.debug("In getRequestDetails");

		User user = (User) session.getAttribute("Session_UserDetails");
		if (user == null) {
			return "redirect:/logout";
		}

		ResponseEntity<Map> taskContentResp = restTemplate.getForEntity(this.omsProcessRootURL + "/users/" + user.getUserName() + "/tasks/"
				+ taskId + "/content", Map.class);

		if (taskContentResp.getStatusCode() == HttpStatus.OK) {

			ObjectMapper objMapper = new ObjectMapper();
			Long astReqId = objMapper.convertValue(taskContentResp.getBody().get("assetReqIdIN"), Long.class);
			AssetRequest astReq = entityService.getByURL(this.omsRestRootURL + "/assetrequests/" + astReqId, AssetRequest.class);

			ResponseEntity<TaskSummary> taskSummaryResp = restTemplate.getForEntity(this.omsProcessRootURL + "/users/" + user.getUserName()
					+ "/tasks/" + taskId + "", TaskSummary.class);
			if (taskSummaryResp.getStatusCode() == HttpStatus.OK) {
				model.addAttribute("taskSummary", taskSummaryResp.getBody());
				session.setAttribute("Session_taskSummary", taskSummaryResp.getBody());
			} else {
				LOG.error("Error fetching task summary data for task id: {0}", taskId);
			}
			model.addAttribute("assetRequest", astReq);
		} else {
			model.addAttribute("message", "Problem fetching tasks for you. Please contact Admin.");
		}
		return "assetAssignmentForm";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/requests/search/products")
	public String searchUnallocatedProducts(@RequestParam(name = "id") long id, @RequestParam(name = "idProduct") long idProduct,
			Model model, HttpSession session) {
		LOG.debug("In searchUnallocatedProducts");
		AssetRequest astReq = entityService.getByURL(this.omsRestRootURL + "/assetrequests/" + id, AssetRequest.class);
		StockRegister[] unAllocatedAssets = entityService.getByURL(this.omsRestRootURL + "/stores/stocks/unassigned/products/" + idProduct,
				StockRegister[].class);
		if (unAllocatedAssets != null) {
			model.addAttribute("unAllocatedAssets", Arrays.asList(unAllocatedAssets));
		} else {
			model.addAttribute("no.result", "Materials are not available in store for this product.");
		}
		model.addAttribute("assetRequest", astReq);
		model.addAttribute("formSelection", new FormSelection());
		model.addAttribute("viewonly", true);
		return "assetAssignmentForm";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/requests/tasks/complete")
	public String assignAssetsTouser(@ModelAttribute FormSelection formData, Model model, HttpSession session,
			@RequestParam boolean approval) {
		LOG.debug("In assignAssetsTouser");
		User user = (User) session.getAttribute("Session_UserDetails");
		TaskSummary taskSummary = (TaskSummary) session.getAttribute("Session_taskSummary");
		AssetRequest astReq = entityService.getByURL(this.omsRestRootURL + "/assetrequests/" + formData.getId(), AssetRequest.class);

		if (approval) {
			for (long astId : formData.getSelectedIds()) {
				StockRegister register = entityService.getByURL(this.omsRestRootURL + "/stores/stocks/" + astId, StockRegister.class);

				if (register != null) {
					register.setAssignedTo(astReq.getUserId());
					if (!(entityService.putByURL(this.omsRestRootURL + "/stores/stocks/" + astId, register))) {
						LOG.error("Unable to assign id " + astId + " to user :" + astReq.getUserId());
					}
				} else {
					LOG.error("No asset available for selected id:" + astId);
				}
			}

			astReq.setStatus(Status.APPROVED);
		} else {
			astReq.setStatus(Status.REJECTED);
		}
		astReq.setComments(formData.getComments());
		astReq.setApprovedBy(user.getUserName());

		if (entityService.putByURL(this.omsRestRootURL + "/assetrequests/" + formData.getId(), astReq)) {
			Map<String, Object> taskData = new HashMap<String, Object>();
			taskData.put("assetAssignStatusOUT", approval);
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
