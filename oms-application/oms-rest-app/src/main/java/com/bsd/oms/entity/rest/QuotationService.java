package com.bsd.oms.entity.rest;

import java.net.URI;

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

import com.bsd.oms.entity.PurchaseOrder;
import com.bsd.oms.entity.Quotation;
import com.bsd.oms.repo.PurchaseOrderRepository;
import com.bsd.oms.repo.QuotationRepository;

@RestController
@RequestMapping(path = "/quotations")
public class QuotationService {

	private static Logger LOG = LoggerFactory.getLogger(QuotationService.class);

	@Autowired
	private QuotationRepository quotationRepo;
	
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepo;

	/**
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createQuotation(@RequestBody Quotation quotation) {
		LOG.debug("Create new quotation with (" + quotation.toString() + " )");
		System.out.println("Create new quotation with (" + quotation.toString() + " )");

		quotation = quotationRepo.save(quotation);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(quotation.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	/**
	 * 
	 * @param quotation
	 * @return
	 */
	@PutMapping(path = "/{quotationId}", consumes = "application/json")
	public ResponseEntity<Quotation> updateQuotation(@PathVariable long quotationId, @RequestBody Quotation quotation) {
		LOG.debug("Update quotation with (" + quotation.toString() + " )");

		quotation.setId(quotationId);
		quotation = quotationRepo.save(quotation);

		return ResponseEntity.ok(quotation);
	}

	/**
	 * 
	 * @param quotation
	 * @return
	 */
	@GetMapping(path = "/{quotationId}")
	public ResponseEntity<Quotation> getQuotation(@PathVariable long quotationId) {
		LOG.debug("Get quotation for id " + quotationId);

		Quotation quotation = quotationRepo.findOne(quotationId);

		return ResponseEntity.ok(quotation);
	}

	/**
	 * 
	 * @param quotation
	 * @return
	 */
	@DeleteMapping(path = "/{quotationId}")
	public ResponseEntity<?> deleteQuotation(@PathVariable long quotationId) {
		LOG.debug("delete quotation for id " + quotationId);

		quotationRepo.delete(quotationId);

		return ResponseEntity.noContent().build();
	}

	/**
	 * 
	 * @param quotation
	 * @return
	 */
	@GetMapping(path = "/{quotationId}/purchaseorder")
	public ResponseEntity<PurchaseOrder> getPurchaseOrderByQuotation(@PathVariable long quotationId) {
		LOG.debug("Get PurchaseOrder for by quotation id " + quotationId);

		return ResponseEntity.ok(purchaseOrderRepo.getByIdQuotation(quotationId));
	}
}
