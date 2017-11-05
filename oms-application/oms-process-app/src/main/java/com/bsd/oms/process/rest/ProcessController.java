package com.bsd.oms.process.rest;

import java.util.List;

import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bsd.oms.PropertyConfig;
import com.bsd.oms.jbpm.JBPMConnection;

@RestController
public class ProcessController {

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
	public ResponseEntity<List<ProcessDefinition>> getAllProcessDefinitions() {
		KieServicesClient kieServerClient = jbpmConnection.getNewSession(propertyConfig.getBpmServerUserName());
		// query for all available process definitions
		QueryServicesClient queryClient = kieServerClient
				.getServicesClient(QueryServicesClient.class);
		List<ProcessDefinition> processes = queryClient.findProcesses(0, 10);
		System.out.println("\t######### Available processes" + processes);
		return ResponseEntity.ok(processes);
	}

	/**
	 * Get all process instances
	 * 
	 * @return
	 */
	@GetMapping(path = "/process/instances")
	public ResponseEntity<List<ProcessInstance>> getProcessInstances() {
		KieServicesClient kieServerClient = jbpmConnection.getNewSession(propertyConfig.getBpmServerUserName());

		// query for all available process definitions
		QueryServicesClient queryClient = kieServerClient
				.getServicesClient(QueryServicesClient.class);

		List<ProcessInstance> processInstances = queryClient
				.findProcessInstances(0, 10);

		System.out.println("\t######### Process Instances: " + processInstances);

		return ResponseEntity.ok(processInstances);
	}
	
	
	/**
	 * Get all task
	 * @return
	 */
	@GetMapping("/process/{processInstanceId}/tasks")
	public ResponseEntity<List<TaskSummary>> getTasks(@PathVariable long processInstanceId) {
		System.out.println("Getting all tasks for processInstanceId:" + processInstanceId);
		KieServicesClient kieServerClient = jbpmConnection.getNewSession(propertyConfig.getBpmServerUserName());
		UserTaskServicesClient taskClient = kieServerClient
				.getServicesClient(UserTaskServicesClient.class);
		// find available tasks
		List<TaskSummary> tasks = taskClient.findTasksByStatusByProcessInstanceId(processInstanceId, null, 0, 10);
		System.out.println("\t######### Tasks: " + tasks);
		return ResponseEntity.ok(tasks);
	}

}
