package com.bsd.oms.process.rest;

import io.swagger.annotations.Api;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.kie.server.client.KieServicesClient;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bsd.oms.PropertyConfig;
import com.bsd.oms.jbpm.JBPMConnection;
import com.bsd.oms.process.OrderDetails;

@RestController
@RequestMapping(path = "/process/orders")
@Api(tags =  { "Process", "Orders" })
public class OrderProcessController {

	private static Logger LOG = LoggerFactory
			.getLogger(OrderProcessController.class);

	@Autowired
	private JBPMConnection jbpmConnection;

	@Autowired
	private PropertyConfig propertyConfig;

	/**
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> placeOrder(@RequestBody OrderDetails order) {
		LOG.debug("placeOrder with " + order.toString());
		KieServicesClient kieServerClient = jbpmConnection.getNewSession();

		ProcessServicesClient processServicesClient = kieServerClient
				.getServicesClient(ProcessServicesClient.class);
		// Create an instance of the custom class
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("order", order);
		// Start the process with custom class
		long processInstanceId = processServicesClient.startProcess(
				"oms-container", "OMS-Process.OrderProcessing", variables);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(processInstanceId).toUri();

		return ResponseEntity.created(location).build();
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping("/{taskId}")
	public OrderDetails getProcessOrderDetailsByTaskId(@PathVariable long taskId) {
		System.out.println("Getting all tasks for id:" + taskId);
		KieServicesClient kieServerClient = jbpmConnection.getNewSession();
		UserTaskServicesClient taskClient = kieServerClient
				.getServicesClient(UserTaskServicesClient.class);

		Map<String, Object> inputData = taskClient.getTaskInputContentByTaskId(
				propertyConfig.getJbpmContainerId(), taskId);
		OrderDetails order = (OrderDetails) inputData.get("orderIn");
		System.out.println("Order Input for HS: " + order.toString());

		return order;
	}

	/**
	 * 
	 * @return
	 */
	@PostMapping("/{userId}/{taskId}/complete")
	public ResponseEntity<?> completeProcessOrderDetailsByTaskId(
			@PathVariable String userId, @PathVariable long taskId,
			@RequestBody OrderDetails order) {
		System.out.println("Completing human task for id:" + taskId
				+ " by user:" + userId);
		KieServicesClient kieServerClient = jbpmConnection.getNewSession();
		UserTaskServicesClient taskClient = kieServerClient
				.getServicesClient(UserTaskServicesClient.class);

		Map<String, Object> outputData = new HashMap<String, Object>();
		outputData.put("orderOut", order);

		taskClient.startTask(propertyConfig.getJbpmContainerId(), taskId,
				userId);

		taskClient.completeTask(propertyConfig.getJbpmContainerId(), taskId,
				userId, outputData);

		System.out.println("Order Process re-initiated.");

		return ResponseEntity.ok().build();
	}
}
