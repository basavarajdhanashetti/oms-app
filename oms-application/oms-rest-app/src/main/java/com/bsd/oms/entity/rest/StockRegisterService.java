package com.bsd.oms.entity.rest;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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

import com.bsd.oms.entity.StockRegister;
import com.bsd.oms.repo.StockRegisterRepository;

@RestController
@RequestMapping(path = "/stores")
public class StockRegisterService {

	private static Logger LOG = LoggerFactory.getLogger(StockRegisterService.class);

	@Autowired
	private StockRegisterRepository registerRepository;

	@Autowired
	private EntityManager entityManager;

	/**
	 * 
	 * @param stockRegister
	 * @return
	 */
	@PostMapping(path = "/stocks", consumes = "application/json")
	public ResponseEntity<?> createStockRegister(@RequestBody StockRegister stockRegister) {
		LOG.debug("Create new inwardRegister with (" + stockRegister.toString() + " )");
		stockRegister = registerRepository.save(stockRegister);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(stockRegister.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * 
	 * @param report
	 * @return
	 */
	@PutMapping(path = "/stocks/{stockId}", consumes = "application/json")
	public ResponseEntity<StockRegister> updateStockRegister(@PathVariable long stockId, @RequestBody StockRegister stockRegister) {
		LOG.debug("Update StockRegister with (" + stockId + " )");
		stockRegister.setId(stockId);
		stockRegister = registerRepository.save(stockRegister);
		return ResponseEntity.ok(stockRegister);
	}

	/**
	 * 
	 * @param stockId
	 * @return
	 */
	@GetMapping(path = "/stocks/{stockId}")
	public ResponseEntity<StockRegister> getStockRegister(@PathVariable long stockId) {
		LOG.debug("Get StockRegister for id " + stockId);
		StockRegister storeInwardRegister = registerRepository.findOne(stockId);
		return ResponseEntity.ok(storeInwardRegister);
	}

	/**
	 * 
	 * @param stockId
	 * @return
	 */
	@DeleteMapping(path = "/stocks/{stockId}")
	public ResponseEntity<?> deleteStockRegister(@PathVariable long stockId) {
		LOG.debug("delete register for id " + stockId);
		registerRepository.delete(stockId);
		return ResponseEntity.noContent().build();
	}

	/**
	 * 
	 * @param stockId
	 * @return
	 */
	@GetMapping(path = "/stocks/unassigned/products/{productId}")
	public ResponseEntity<List<StockRegister>> getUnassignedStockRegisters(@PathVariable long productId) {
		LOG.debug("Get StockRegister for productId " + productId);

		Query qry = entityManager.createNamedQuery("StockRegister.findUnallocatedAssetsForProduct").setParameter("idProduct", productId);

		@SuppressWarnings("unchecked")
		List<StockRegister> storeInwardRegister = qry.getResultList();

		return ResponseEntity.ok(storeInwardRegister);
	}

	/**
	 * 
	 * @param stockId
	 * @return
	 */
	@GetMapping(path = "/stocks/assigned/users/{userId}")
	public ResponseEntity<List<StockRegister>> getAssignedStockRegisters(@PathVariable String userId) {
		LOG.debug("Get StockRegister for userId " + userId);

		Query qry = entityManager.createNamedQuery("StockRegister.findAllocatedAssets").setParameter("assignedTo", userId);

		@SuppressWarnings("unchecked")
		List<StockRegister> storeInwardRegister = qry.getResultList();

		return ResponseEntity.ok(storeInwardRegister);
	}
}
