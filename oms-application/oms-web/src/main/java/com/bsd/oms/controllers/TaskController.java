package com.bsd.oms.controllers;

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
import com.bsd.oms.entity.User;

@Controller
@RequestMapping("/process/tasks")
public class TaskController {

	private static final Logger LOG = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${oms-process-url}")
	private String omsProcessRootURL;

	@GetMapping
	public String getMyTasks(Model model, HttpSession session) {

		User user = (User) session.getAttribute("Session_UserDetails");
		if(user == null){
			return "rediect:/";
		}

		ResponseEntity<List> taskResponse = restTemplate.getForEntity(this.omsProcessRootURL + "/users/" + user.getUserName() + "/tasks", List.class);

		if (taskResponse.getStatusCode() == HttpStatus.OK) {
			List taskList = taskResponse.getBody();
			System.out.println("task list: " + taskList);
			model.addAttribute("tasks", taskList);
			model.addAttribute("message", "Here are the list of Tasks for you");
		} else {
			model.addAttribute("message", "Problem fetching tasks for you. Please contact Admin.");
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
	@GetMapping(path = "/{taskId}/claim")
	public String claimTasks(Model model, HttpSession session, @PathVariable long taskId) {

		User user = (User) session.getAttribute("Session_UserDetails");
		if(user == null){
			return "rediect:/";
		}

		ResponseEntity<Message> messageEntity = restTemplate.getForEntity(this.omsProcessRootURL + "/users/" + user.getUserName() + "/tasks/" + taskId
				+ "/claim", Message.class);

		if (messageEntity.getStatusCode() == HttpStatus.OK) {
			String msg = messageEntity.getBody().getMessage();
			model.addAttribute("message", msg);
		} else {
			model.addAttribute("message", "Problem claiming tasks for you. Please contact Admin.");
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
	@GetMapping(path = "/{taskId}/start")
	public String startTasks(Model model, HttpSession session, @PathVariable long taskId) {

		User user = (User) session.getAttribute("Session_UserDetails");
		if(user == null){
			return "rediect:/";
		}

		ResponseEntity<Message> messageEntity = restTemplate.getForEntity(this.omsProcessRootURL + "/users/" + user.getUserName() + "/tasks/" + taskId
				+ "/start", Message.class);

		if (messageEntity.getStatusCode() == HttpStatus.OK) {
			String msg = messageEntity.getBody().getMessage();
			model.addAttribute("message", msg);
		} else {
			model.addAttribute("message", "Problem starting tasks for you. Please contact Admin.");
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
	@GetMapping(path = "/{taskId}/release")
	public String releaseTasks(Model model, HttpSession session, @PathVariable long taskId) {

		User user = (User) session.getAttribute("Session_UserDetails");
		if(user == null){
			return "rediect:/";
		}

		ResponseEntity<Message> messageEntity = restTemplate.getForEntity(this.omsProcessRootURL + "/users/" + user.getUserName() + "/tasks/" + taskId
				+ "/release", Message.class);

		if (messageEntity.getStatusCode() == HttpStatus.OK) {
			String msg = messageEntity.getBody().getMessage();
			model.addAttribute("message", msg);
		} else {
			model.addAttribute("message", "Problem releasing tasks for you. Please contact Admin.");
		}
		return "redirect:/process/tasks";
	}
}
