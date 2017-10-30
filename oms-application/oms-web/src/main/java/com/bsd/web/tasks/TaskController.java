package com.bsd.web.tasks;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.bsd.oms.domain.Message;

@Controller
@RequestMapping("/process/tasks")
public class TaskController {

	private static final Logger LOG = LoggerFactory
			.getLogger(TaskController.class);
	
	@Autowired
	private RestTemplate restTemplate;

	@Value("${oms-rest-url}")
	private String omsRestRootURL;

	@GetMapping
	public String getMyTasks(Model model, HttpSession session) {

		String userId = (String) session.getAttribute("userId");
		
		if(userId == null){
			userId = "bpmsAdmin";
		}
		
		ResponseEntity<List> taskResponse = restTemplate.getForEntity(
				this.omsRestRootURL + "/process/"+userId+"/tasks", List.class);

		if (taskResponse.getStatusCode() == HttpStatus.OK) {
			List taskList = taskResponse.getBody();
			System.out.println("task list: " + taskList);
			model.addAttribute("tasks", taskList);
			model.addAttribute("message", "Here are the list of Tasks for you");
		} else {
			model.addAttribute("message",
					"Problem fetching tasks for you. Please contact Admin.");
		}
		return "taskList";
	}
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @param taskId
	 * @return
	 */
	@GetMapping(path="/{taskId}/claim")
	public String claimTasks(Model model, HttpSession session, @PathVariable long taskId) {

		String userId = (String) session.getAttribute("userId");
		
		if(userId == null){
			userId = "bpmsAdmin";
		}
		
		ResponseEntity<Message> messageEntity = restTemplate.getForEntity(
				this.omsRestRootURL + "/process/"+userId+"/tasks/"+taskId + "/claim", Message.class);

		if (messageEntity.getStatusCode() == HttpStatus.OK) {
			String msg = messageEntity.getBody().getMessage();
			model.addAttribute("message", msg);
		} else {
			model.addAttribute("message",
					"Problem claiming tasks for you. Please contact Admin.");
		}
		return "redirect:/process/tasks";
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @param taskId
	 * @return
	 */
	@GetMapping(path="/{taskId}/start")
	public String startTasks(Model model, HttpSession session, @PathVariable long taskId) {

		String userId = (String) session.getAttribute("userId");
		
		if(userId == null){
			userId = "bpmsAdmin";
		}
		
		ResponseEntity<Message> messageEntity = restTemplate.getForEntity(
				this.omsRestRootURL + "/process/"+userId+"/tasks/"+taskId + "/start", Message.class);

		if (messageEntity.getStatusCode() == HttpStatus.OK) {
			String msg = messageEntity.getBody().getMessage();
			model.addAttribute("message", msg);
		} else {
			model.addAttribute("message",
					"Problem starting tasks for you. Please contact Admin.");
		}
		return "redirect:/process/tasks";
	}
	

	/**
	 * 
	 * @param model
	 * @param session
	 * @param taskId
	 * @return
	 */
	@GetMapping(path="/{taskId}/release")
	public String releaseTasks(Model model, HttpSession session, @PathVariable long taskId) {

		String userId = (String) session.getAttribute("userId");
		
		if(userId == null){
			userId = "bpmsAdmin";
		}
		
		ResponseEntity<Message> messageEntity = restTemplate.getForEntity(
				this.omsRestRootURL + "/process/"+userId+"/tasks/"+taskId + "/release", Message.class);

		if (messageEntity.getStatusCode() == HttpStatus.OK) {
			String msg = messageEntity.getBody().getMessage();
			model.addAttribute("message", msg);
		} else {
			model.addAttribute("message",
					"Problem releasing tasks for you. Please contact Admin.");
		}
		return "redirect:/process/tasks";
	}
}
