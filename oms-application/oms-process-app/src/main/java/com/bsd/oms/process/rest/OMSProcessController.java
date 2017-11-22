package com.bsd.oms.process.rest;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.kie.server.client.KieServicesClient;
import org.kie.server.client.ProcessServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bsd.oms.PropertyConfig;
import com.bsd.oms.jbpm.JBPMConnection;
import com.bsd.oms.process.PurchaseRequest;

@RestController
public class OMSProcessController {

	private static Logger LOG = LoggerFactory.getLogger(OMSProcessController.class);

	@Autowired
	private JBPMConnection jbpmConnection;

	@Autowired
	private PropertyConfig propertyConfig;

	/**
	 * 
	 * @param purchaseRequest
	 * @return
	 */
	@PostMapping(path = "/users/{userId}/purchase-requests", consumes = "application/json")
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
	 * @param purchaseRequest
	 * @return
	 */
	@PostMapping(path = "/users/{userId}/purchaseorders/{poId}/inwards")
	public ResponseEntity<?> placeStoreInwardRequest(@PathVariable String userId, @PathVariable long poId) {
		LOG.debug("Placing Store inward Process for purchase order id " + poId);
		KieServicesClient kieServerClient = jbpmConnection.getNewSession(userId);

		ProcessServicesClient processServicesClient = kieServerClient.getServicesClient(ProcessServicesClient.class);

		// Create an instance of the custom class
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("purchaseOrderId", poId);

		// Start the process with custom class
		long processInstanceId = processServicesClient.startProcess(propertyConfig.getProcurementContainer(),
				propertyConfig.getStoreInwardProcessId(), variables);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(processInstanceId).toUri();

		return ResponseEntity.created(location).build();
	}
	
	/**
	 * 
	 * @param userId
	 * @param assetReqId
	 * @return
	 */
	@PostMapping(path = "/users/{userId}/asset-requests/{assetReqId}")
	public ResponseEntity<?> placeAssetAssignmentRequest(@PathVariable String userId, @PathVariable long assetReqId) {
		LOG.debug("Placing Asset Assignment Process for id " + assetReqId);
		KieServicesClient kieServerClient = jbpmConnection.getNewSession(userId);

		ProcessServicesClient processServicesClient = kieServerClient.getServicesClient(ProcessServicesClient.class);

		// Create an instance of the custom class
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("assetReqId", assetReqId);

		// Start the process with custom class
		long processInstanceId = processServicesClient.startProcess(propertyConfig.getProcurementContainer(),
				propertyConfig.getAssetAssignProcessId(), variables);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(processInstanceId).toUri();

		return ResponseEntity.created(location).build();
	}
}
