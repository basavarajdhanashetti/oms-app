package com.bsd.oms.process.rest;

import io.swagger.annotations.Api;

import java.util.List;
import java.util.Map;

import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.TaskInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.QueryServicesClient;
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
import com.bsd.oms.rest.util.TaskUtil;

@RestController
@Api(tags =  {"Process", "Tasks"})
public class TaskController {

	@Autowired
	private JBPMConnection jbpmConnection;

	@Autowired
	private PropertyConfig propertyConfig;

	/**
	 * Get all process definitions
	 * 
	 * @return
	 */
	@GetMapping("/process")
	public List<ProcessDefinition> getAllProcessDefinitions() {
		KieServicesClient kieServerClient = jbpmConnection.getNewSession();
		// query for all available process definitions
		QueryServicesClient queryClient = kieServerClient
				.getServicesClient(QueryServicesClient.class);
		List<ProcessDefinition> processes = queryClient.findProcesses(0, 10);
		System.out.println("\t######### Available processes" + processes);
		return processes;
	}

	/**
	 * Get all process instances
	 * 
	 * @return
	 */
	@GetMapping(path = "/process/instances")
	public List<ProcessInstance> getProcessInstances() {
		KieServicesClient kieServerClient = jbpmConnection.getNewSession();

		// query for all available process definitions
		QueryServicesClient queryClient = kieServerClient
				.getServicesClient(QueryServicesClient.class);

		List<ProcessInstance> processInstances = queryClient
				.findProcessInstances(0, 10);

		System.out
				.println("\t######### Process Instances: " + processInstances);

		return processInstances;
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping("/process/{user}/tasks")
	public List<TaskSummary> getTasks(@PathVariable String user) {
		System.out.println("Getting all tasks for user:" + user);
		KieServicesClient kieServerClient = jbpmConnection.getNewSession();
		UserTaskServicesClient taskClient = kieServerClient
				.getServicesClient(UserTaskServicesClient.class);
		// find available tasks
		List<TaskSummary> tasks = taskClient.findTasksAssignedAsPotentialOwner(
				user, 0, 10);
		System.out.println("\t######### Tasks: " + tasks);
		return tasks;
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping("/process/{user}/tasks/{taskId}/claim")
	public ResponseEntity<Message> claimTask(@PathVariable String user, @PathVariable long taskId) {
		System.out.println("Claim task for user:" + user + " task id:"+ taskId);
		KieServicesClient kieServerClient = jbpmConnection.getNewSession();
		UserTaskServicesClient taskClient = kieServerClient.getServicesClient(UserTaskServicesClient.class);
		TaskInstance taskInstance = taskClient.findTaskById(taskId);
		try {
			if ("Ready".equals(taskInstance.getStatus())) {
				taskClient.claimTask("oms-container", taskId, user);
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
	@GetMapping("/process/{user}/tasks/{taskId}/start")
	public ResponseEntity<Message> startTask(@PathVariable String user, @PathVariable long taskId) {
		System.out.println("Start task for user:" + user + " task id:"+ taskId);
		KieServicesClient kieServerClient = jbpmConnection.getNewSession();
		UserTaskServicesClient taskClient = kieServerClient.getServicesClient(UserTaskServicesClient.class);
		TaskInstance taskInstance = taskClient.findTaskById(taskId);
		try {
			if ("Reserved".equals(taskInstance.getStatus())) {
				taskClient.startTask("oms-container", taskId, user);
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
	@GetMapping("/process/{user}/tasks/{taskId}/release")
	public ResponseEntity<Message> releaseTask(@PathVariable String user, @PathVariable long taskId) {
		System.out.println("Release task for user:" + user + " task id:"+ taskId);
		KieServicesClient kieServerClient = jbpmConnection.getNewSession();
		UserTaskServicesClient taskClient = kieServerClient.getServicesClient(UserTaskServicesClient.class);
		TaskInstance taskInstance = taskClient.findTaskById(taskId);
		try {
			if ("Reserved".equals(taskInstance.getStatus()) || "InProgress".equals(taskInstance.getStatus())) {
				taskClient.releaseTask("oms-container", taskId, user);
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
	@PostMapping("/process/{user}/tasks/{taskId}/complete")
	public ResponseEntity<Message> completeTask(@PathVariable String user, @PathVariable long taskId, @RequestBody Map<String, Object> taskData) {
		System.out.println("Complete task for user:" + user + " task id:"+ taskId);
		KieServicesClient kieServerClient = jbpmConnection.getNewSession();
		UserTaskServicesClient taskClient = kieServerClient.getServicesClient(UserTaskServicesClient.class);
				
		TaskInstance taskInstance = taskClient.findTaskById(taskId);
		
		Map<String, Object> outputData = TaskUtil.getMapperByTask(taskInstance.getName(), taskData);
		try {
			if (taskInstance.getStatus().equals("InProgress")) {
				taskClient.completeTask("oms-container", taskId, user, outputData);
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
