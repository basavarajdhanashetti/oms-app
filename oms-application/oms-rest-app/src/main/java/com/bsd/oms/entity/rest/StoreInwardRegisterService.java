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

import com.bsd.oms.entity.StockRegister;
import com.bsd.oms.entity.StoreInwardItem;
import com.bsd.oms.entity.StoreInwardRegister;
import com.bsd.oms.repo.StockRegisterRepository;
import com.bsd.oms.repo.StoreInwardItemRepository;
import com.bsd.oms.repo.StoreInwardRegisterRepository;

@RestController
@RequestMapping(path = "/stores")
public class StoreInwardRegisterService {

	private static Logger LOG = LoggerFactory.getLogger(StoreInwardRegisterService.class);

	@Autowired
	private StoreInwardRegisterRepository registerRepository;

	@Autowired
	private StoreInwardItemRepository inwardItemRepository;
	
	@Autowired
	private StockRegisterRepository stockRegisterRepository;
	

	/**
	 * 
	 * @param inwardRegister
	 * @return
	 */
	@PostMapping(path="/inwards", consumes = "application/json")
	public ResponseEntity<?> createStoreInwardRegister(@RequestBody StoreInwardRegister inwardRegister) {
		LOG.debug("Create new inwardRegister with (" + inwardRegister.toString() + " )");
		inwardRegister = registerRepository.save(inwardRegister);
		for (StoreInwardItem inwardItem : inwardRegister.getItems()) {
			inwardItem.setIdInwardRegister(inwardRegister.getId());
			inwardItemRepository.save(inwardItem);
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(inwardRegister.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * 
	 * @param report
	 * @return
	 */
	@PutMapping(path = "/inwards/{registerId}", consumes = "application/json")
	public ResponseEntity<StoreInwardRegister> updateStoreInwardRegister(@PathVariable long registerId,
			@RequestBody StoreInwardRegister inwardRegister) {
		LOG.debug("Update StoreInwardRegister with (" + registerId + " )");
		inwardRegister.setId(registerId);
		inwardRegister = registerRepository.save(inwardRegister);
		for (StoreInwardItem inwardItem : inwardRegister.getItems()) {
			inwardItem.setIdInwardRegister(inwardRegister.getId());
			inwardItemRepository.save(inwardItem);
		}
		return ResponseEntity.ok(inwardRegister);
	}

	/**
	 * 
	 * @param registerId
	 * @return
	 */
	@GetMapping(path = "/inwards/{registerId}")
	public ResponseEntity<StoreInwardRegister> getStoreInwardRegister(@PathVariable long registerId) {
		LOG.debug("Get StoreInwardRegister for id " + registerId);

		StoreInwardRegister storeInwardRegister = registerRepository.findOne(registerId);

		return ResponseEntity.ok(storeInwardRegister);
	}

	/**
	 * 
	 * @param registerId
	 * @return
	 */
	@DeleteMapping(path = "/inwards/{registerId}")
	public ResponseEntity<?> deleteStoreInwardRegister(@PathVariable long registerId) {
		LOG.debug("delete register for id " + registerId);

		StoreInwardRegister storeInwardRegister = registerRepository.findOne(registerId);

		if (storeInwardRegister != null) {

			for (StoreInwardItem inwardItem : storeInwardRegister.getItems()) {
				inwardItemRepository.delete(inwardItem);
			}

			registerRepository.delete(registerId);
		}
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * 
	 * @param registerId
	 * @return
	 */
	@GetMapping(path = "/purchaseorders/{poId}/inwards")
	public ResponseEntity<StoreInwardRegister> getStoreInwardRegisterByPOId(@PathVariable long poId) {
		LOG.debug("Get StoreInwardRegister for id " + poId);

		StoreInwardRegister storeInwardRegister = registerRepository.getByIdPurchaseOrder(poId);

		return ResponseEntity.ok(storeInwardRegister);
	}
	
	/**
	 * 
	 * @param registerId
	 * @param itemId
	 * @return
	 */
	@GetMapping(path = "/inwards/{registerId}/items/{itemId}/stocks")
	public ResponseEntity<List<StockRegister>> getStoreInwardRegister(@PathVariable long registerId, @PathVariable long itemId) {
		LOG.debug("Get StoreInwardRegister for id " + registerId);

		List<StockRegister> lst = stockRegisterRepository.getByIdInwardItem(itemId);

		return ResponseEntity.ok(lst);
	}
}
