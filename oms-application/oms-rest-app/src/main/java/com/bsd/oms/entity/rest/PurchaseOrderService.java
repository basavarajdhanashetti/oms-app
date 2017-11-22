package com.bsd.oms.entity.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bsd.oms.entity.PurchaseOrder;
import com.bsd.oms.repo.PurchaseOrderRepository;
import com.bsd.oms.utils.OMSDateUtil;


@RestController
@RequestMapping(path = "/purchaseorders")
public class PurchaseOrderService {

	private static Logger LOG = LoggerFactory.getLogger(PurchaseOrderService.class);

	
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepo;
	
	@Autowired
	private EntityManager entityManager;
	
	/**
	 * 
	 * @param quotation
	 * @return
	 */
	@GetMapping(path = "/{poId}")
	public ResponseEntity<PurchaseOrder> getPurchaseOrder(@PathVariable long poId) {
		LOG.debug("Get quotation for id " + poId);

		PurchaseOrder purchaseOrder = purchaseOrderRepo.findOne(poId);

		return ResponseEntity.ok(purchaseOrder);
	}
	
	/**
	 * 
	 * @param quotation
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<List<PurchaseOrder>> getPurchaseOrders(@RequestParam(name="fromDate") String fromDate, @RequestParam(name="toDate") String toDate) {
		LOG.debug("Get getPurchaseOrders for fromDate :" + fromDate + ", toDate: "+ toDate);
		
		Query qry = entityManager.createNamedQuery("PurchaseOrder.getAllBetweenDates").setParameter("fromDate", OMSDateUtil.toDateTime(fromDate)).setParameter("toDate", OMSDateUtil.toDateTime(toDate));

		@SuppressWarnings("unchecked")
		List<PurchaseOrder> poLst = qry.getResultList();

		return ResponseEntity.ok(poLst);
	}
}
