package com.bsd.oms.entity.rest;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bsd.oms.entity.PurchaseRequest;
import com.bsd.oms.entity.Quotation;
import com.bsd.oms.repo.PurchaseRequestRepository;
import com.bsd.oms.repo.QuotationRepository;

@RestController
@RequestMapping(path = "/purchases")
public class PurchaseRequestService {

	private static Logger LOG = LoggerFactory.getLogger(PurchaseRequestService.class);
	
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepo; 
	
	@Autowired
	private QuotationRepository quotationRepo; 

	/**
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createPurchaseRequest(@RequestBody PurchaseRequest purchaseRequest) {
		LOG.debug("Create new purchaseRequest with (" + purchaseRequest.toString() + " )");
		System.out.println("Create new purchaseRequest with (" + purchaseRequest.toString() + " )");
		purchaseRequest.setId(0);
		purchaseRequest = purchaseRequestRepo.save(purchaseRequest);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(purchaseRequest.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	/**
	 * 
	 * @param purchaseRequest
	 * @return
	 */
	@PutMapping(path="/{purchaseRequestId}",consumes = "application/json")
	public ResponseEntity<PurchaseRequest> updatePurchaseRequest(@PathVariable long purchaseRequestId,@RequestBody PurchaseRequest purchaseRequest) {
		LOG.debug("Update purchaseRequest with (" + purchaseRequest.toString() + " )");
			
		purchaseRequest.setId(purchaseRequestId);
		purchaseRequest = purchaseRequestRepo.save(purchaseRequest);
		
		return ResponseEntity.ok(purchaseRequest);
	}
	
	/**
	 * 
	 * @param purchaseRequest
	 * @return
	 */
	@GetMapping(path="/{purchaseRequestId}")
	public ResponseEntity<PurchaseRequest> getPurchaseRequest(@PathVariable long purchaseRequestId) {
		LOG.debug("Get purchaseRequest for id " + purchaseRequestId );

		PurchaseRequest purchaseRequest = purchaseRequestRepo.findOne(purchaseRequestId);

		return ResponseEntity.ok(purchaseRequest);
	}
	
	/**
	 * 
	 * @param purchaseRequest
	 * @return
	 */
	@DeleteMapping(path="/{purchaseRequestId}")
	public ResponseEntity<?> deletePurchaseRequest(@PathVariable long purchaseRequestId) {
		LOG.debug("delete purchaseRequest for id " + purchaseRequestId );

		purchaseRequestRepo.delete(purchaseRequestId);

		return ResponseEntity.noContent().build();
	}

	/**
	 * 
	 * @param purchaseRequest
	 * @return
	 */
	@GetMapping(path="/{purchaseRequestId}/quotations")
	public ResponseEntity<List<Quotation>> getQuotationsForPurchaseRequest(@PathVariable long purchaseRequestId) {
		LOG.debug("Get purchaseRequest for id " + purchaseRequestId );
		return ResponseEntity.ok(quotationRepo.getByIdPurchaseRequest(purchaseRequestId));
	}
}
