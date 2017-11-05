package com.bsd.oms.process.rest;

import java.util.List;
import java.util.Map;

import org.kie.server.api.model.instance.TaskInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bsd.oms.PropertyConfig;
import com.bsd.oms.domain.Message;
import com.bsd.oms.jbpm.JBPMConnection;
import com.bsd.oms.repo.UserRepository;
import com.bsd.oms.rest.util.TaskUtil;

@RestController
public class TaskController {

	@Autowired
	private JBPMConnection jbpmConnection;

	@Autowired
	private PropertyConfig propertyConfig;

	@Autowired
	private UserRepository userRepo;

	/**
	 * 
	 * @return
	 */
	@GetMapping("/users/{user}/tasks")
	public ResponseEntity<List<TaskSummary>> getTasks(@PathVariable String user) {
		System.out.println("Getting all tasks for user:" + user);
		KieServicesClient kieServerClient = jbpmConnection.getNewSession(user);
		UserTaskServicesClient taskClient = kieServerClient.getServicesClient(UserTaskServicesClient.class);
		// find available tasks
		List<TaskSummary> tasks = taskClient.findTasksAssignedAsPotentialOwner(user, 0, 10);
		System.out.println("\t######### Tasks: " + tasks);
		return ResponseEntity.ok(tasks);
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping("/users/{user}/tasks/{taskId}/claim")
	public ResponseEntity<Message> claimTask(@PathVariable String user, @PathVariable long taskId) {
		System.out.println("Claim task for user:" + user + " task id:" + taskId);
		KieServicesClient kieServerClient = jbpmConnection.getNewSession(user);
		UserTaskServicesClient taskClient = kieServerClient.getServicesClient(UserTaskServicesClient.class);
		TaskInstance taskInstance = taskClient.findTaskById(taskId);
		try {
			if ("Ready".equals(taskInstance.getStatus())) {
				taskClient.claimTask(propertyConfig.getProcurementContainer(), taskId, user);
				return ResponseEntity.ok(new Message("Ok"));
			} else {
				return ResponseEntity.ok(new Message("NotOk"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new Message("NotOk"));
		}
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping("/users/{user}/tasks/{taskId}/start")
	public ResponseEntity<Message> startTask(@PathVariable String user, @PathVariable long taskId) {
		System.out.println("Start task for user:" + user + " task id:" + taskId);
		KieServicesClient kieServerClient = jbpmConnection.getNewSession(user);
		UserTaskServicesClient taskClient = kieServerClient.getServicesClient(UserTaskServicesClient.class);
		TaskInstance taskInstance = taskClient.findTaskById(taskId);
		try {
			if ("Reserved".equals(taskInstance.getStatus())) {
				taskClient.startTask(propertyConfig.getProcurementContainer(), taskId, user);
				return ResponseEntity.ok(new Message("Ok"));
			} else {
				return ResponseEntity.ok(new Message("NotOk"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new Message("NotOk"));
		}
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping("/users/{user}/tasks/{taskId}/release")
	public ResponseEntity<Message> releaseTask(@PathVariable String user, @PathVariable long taskId) {
		System.out.println("Release task for user:" + user + " task id:" + taskId);
		KieServicesClient kieServerClient = jbpmConnection.getNewSession(user);
		UserTaskServicesClient taskClient = kieServerClient.getServicesClient(UserTaskServicesClient.class);
		TaskInstance taskInstance = taskClient.findTaskById(taskId);
		try {
			if ("Reserved".equals(taskInstance.getStatus()) || "InProgress".equals(taskInstance.getStatus())) {
				taskClient.releaseTask(propertyConfig.getProcurementContainer(), taskId, user);
				return ResponseEntity.ok(new Message("Ok"));
			} else {
				return ResponseEntity.ok(new Message("NotOk"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new Message("NotOk"));
		}
	}

	/**
	 * 
	 * @return
	 */
	@PostMapping("/users/{user}/tasks/{taskId}/complete")
	public ResponseEntity<Message> completeTask(@PathVariable String user, @PathVariable long taskId,
			@RequestBody Map<String, Object> taskData) {
		System.out.println("Complete task for user:" + user + " task id:" + taskId);
		KieServicesClient kieServerClient = jbpmConnection.getNewSession(user);
		UserTaskServicesClient taskClient = kieServerClient.getServicesClient(UserTaskServicesClient.class);

		TaskInstance taskInstance = taskClient.findTaskById(taskId);

		Map<String, Object> outputData = TaskUtil.getMapperByTask(taskInstance.getName(), taskData);
		try {
			if (taskInstance.getStatus().equals("InProgress")) {
				taskClient.completeTask(propertyConfig.getProcurementContainer(), taskId, user, outputData);
				return ResponseEntity.ok(new Message("Ok"));
			} else {
				return ResponseEntity.ok(new Message("NotOk"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new Message("NotOk"));
		}
	}
}
