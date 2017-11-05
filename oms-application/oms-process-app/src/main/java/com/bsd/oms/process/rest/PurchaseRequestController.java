package com.bsd.oms.process.rest;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bsd.oms.PropertyConfig;
import com.bsd.oms.jbpm.JBPMConnection;
import com.bsd.oms.process.PurchaseRequest;

@RestController
public class PurchaseRequestController {

	private static Logger LOG = LoggerFactory.getLogger(PurchaseRequestController.class);

	@Autowired
	private JBPMConnection jbpmConnection;

	@Autowired
	private PropertyConfig propertyConfig;

	/**
	 * 
	 * @param purchaseRequest
	 * @return
	 */
	@PostMapping(path = "users/{userId}/purchase-requests", consumes = "application/json")
	public ResponseEntity<?> placePurchaseRequest(@PathVariable String userId, @RequestBody PurchaseRequest purchaseRequest) {
		LOG.debug("placePurchaseRequest with " + purchaseRequest.toString());
		KieServicesClient kieServerClient = jbpmConnection.getNewSession(userId);

		ProcessServicesClient processServicesClient = kieServerClient.getServicesClient(ProcessServicesClient.class);

		// Create an instance of the custom class
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("prDetails", purchaseRequest);

		// Start the process with custom class
		long processInstanceId = processServicesClient.startProcess(propertyConfig.getProcurementContainer(),
				propertyConfig.getPurchaseRequestProcessId(), variables);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(processInstanceId).toUri();

		return ResponseEntity.created(location).build();
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping("users/{userId}/purchase-requests/{taskId}")
	public ResponseEntity<PurchaseRequest> getPurchaseRequestByTaskId(@PathVariable String userId, @PathVariable long taskId) {
		System.out.println("Getting PurchaseRequest tasks for id:" + taskId);
		KieServicesClient kieServerClient = jbpmConnection.getNewSession(userId);
		UserTaskServicesClient taskClient = kieServerClient.getServicesClient(UserTaskServicesClient.class);

		Map<String, Object> inputData = taskClient.getTaskInputContentByTaskId(propertyConfig.getProcurementContainer(), taskId);
		PurchaseRequest purchaseReq = (PurchaseRequest) inputData.get("purchaseRequestIN");
		System.out.println("PurchaseRequest : " + purchaseReq.toString());

		return ResponseEntity.ok(purchaseReq);
	}

}
