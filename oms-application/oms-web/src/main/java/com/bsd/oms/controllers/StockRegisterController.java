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
import org.springframework.web.client.RestTemplate;

import com.bsd.oms.domain.Message;
import com.bsd.oms.dto.StockForm;
import com.bsd.oms.dto.StockItemForm;
import com.bsd.oms.entity.StockRegister;
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
public class StockRegisterController {

	private static final Logger LOG = LoggerFactory.getLogger(StockRegisterController.class);

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
	@RequestMapping(method = RequestMethod.GET, path = "/stocks")
	public String getUserAssets(Model model, HttpSession session) {
		LOG.debug("In getUserAssets");
		User user = (User) session.getAttribute("Session_UserDetails");
		StockRegister[] allocatedAssets = entityService.getByURL(this.omsRestRootURL + "/stores/stocks/assigned/users/" + user.getUserName(),
				StockRegister[].class);
		if (allocatedAssets != null) {
			model.addAttribute("allocatedAssets", Arrays.asList(allocatedAssets));
		} else {
			model.addAttribute("no.result", "Materials are not available in store for this product.");
		}
		return "stockList";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/stocks/tasks/{taskId}")
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

		if (taskSummary.getName().equals(TaskEnum.StockRegistration.getTaskName())) {
			LOG.info("task name:" + taskSummary.getName());
			ObjectMapper objMapper = new ObjectMapper();
			long storeInwardRegId = objMapper.convertValue(taskContentMap.get("storeInwardRegIdIN"), Long.class);
			StoreInwardRegister inwardRegister = entityService.getStoreInwardRegister(storeInwardRegId);
			model.addAttribute("inwardRegForm", inwardRegister);
			StockForm stockForm = prepareStockForm(inwardRegister);
			model.addAttribute("stockForm", stockForm);
			return "stockEntryForm";
		}

		model.addAttribute("message", "Problem fetching tasks for you. Please contact Admin.");
		return "redirect:/process/tasks";
	}

	/**
	 * 
	 * @param inwardRegister
	 * @return
	 */
	private StockForm prepareStockForm(StoreInwardRegister inwardRegister) {
		StockForm stockForm = new StockForm(new ArrayList<StockItemForm>());

		for (StoreInwardItem inItem : inwardRegister.getItems()) {

			List<StockRegister> lst = entityService.getStockRegisterByInwardItemId(0, inItem.getId());
			if (lst == null) {
				lst = getListOfItems(inItem);
			}
			stockForm.getStockItems().add(new StockItemForm(inItem, lst));
		}

		return stockForm;
	}

	private List<StockRegister> getListOfItems(StoreInwardItem inItem) {
		List<StockRegister> items = new ArrayList<StockRegister>();
		for (int i = 0; i < inItem.getReceivedQty(); i++) {
			StockRegister stockRegister = new StockRegister();
			stockRegister.setIdInwardItem(inItem.getId());
			stockRegister.setIdProduct(inItem.getIdProduct());
			items.add(stockRegister);
		}
		return items;
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/stocks/save")
	public String saveData(@ModelAttribute StockForm stockForm, Model model, HttpSession session) {
		LOG.debug("In getMyReports ");

		User user = (User) session.getAttribute("Session_UserDetails");

		for (StockItemForm itemForm : stockForm.getStockItems()) {

			for (StockRegister register : itemForm.getRegisters()) {
				register.setEnteredBy(user.getUserName());
				register.setStatus(Status.SAVED);

				ResponseEntity<StockRegister> reportResp = restTemplate.postForEntity(this.omsRestRootURL + "/stores/stocks", register,
						StockRegister.class);

				if (reportResp.getStatusCode() == HttpStatus.CREATED) {
					model.addAttribute("message", "Data saved for later submit.");
				} else {
					model.addAttribute("message", "Problem fetching tasks for you. Please contact Admin.");
				}
			}
		}
		return "redirect:/process/tasks";
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/stocks/complete")
	public String completeTask(@ModelAttribute StockForm stockForm, Model model, HttpSession session) {
		LOG.debug("In completeTask ");

		User user = (User) session.getAttribute("Session_UserDetails");
		TaskSummary taskSummary = (TaskSummary) session.getAttribute("Session_taskSummary");

		for (StockItemForm itemForm : stockForm.getStockItems()) {

			for (StockRegister register : itemForm.getRegisters()) {
				register.setEnteredBy(user.getUserName());
				register.setStatus(Status.APPROVED);

				ResponseEntity<StockRegister> reportResp = restTemplate.postForEntity(this.omsRestRootURL + "/stores/stocks", register,
						StockRegister.class);

				if (reportResp.getStatusCode() == HttpStatus.CREATED) {
					model.addAttribute("message", "Data saved for later submit.");
				} else {
					model.addAttribute("message", "Problem saving data for you. Please contact Admin.");
				}
			}
		}

		Map<String, Object> taskData = new HashMap<String, Object>();

		ResponseEntity<Message> processResponse = restTemplate.postForEntity(this.omsProcessRootURL + "/users/" + user.getUserName()
				+ "/tasks/" + taskSummary.getId() + "/complete", taskData, Message.class);

		if (processResponse.getStatusCode() == HttpStatus.OK) {
			LOG.debug("Task completed now.");
			model.addAttribute("message", "Task completed successfully.");
		} else {
			model.addAttribute("message", "Problem completing tasks for you. Please contact Admin.");
		}
		return "redirect:/process/tasks";
	}
}
