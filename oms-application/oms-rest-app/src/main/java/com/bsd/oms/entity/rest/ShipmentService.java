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

import com.bsd.oms.entity.Product;
import com.bsd.oms.repo.ProductRepository;


@RestController
@RequestMapping(path = "/shipments")
public class ShipmentService {


	private static Logger LOG = LoggerFactory.getLogger(ShipmentService.class);
	
	@Autowired
	private ProductRepository shipmentRepo; 

	/**
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createShipment(@RequestBody Product shipment) {
		LOG.debug("Create new shipment with (" + shipment.toString() + " )");
		System.out.println("Create new shipment with (" + shipment.toString() + " )");
		shipment = shipmentRepo.save(shipment);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(shipment.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	/**
	 * 
	 * @param shipment
	 * @return
	 */
	@PutMapping(path="/{shipmentId}",consumes = "application/json")
	public ResponseEntity<Product> updateShipment(@PathVariable long shipmentId,@RequestBody Product shipment) {
		LOG.debug("Update shipment with (" + shipment.toString() + " )");
			
		shipment.setId(shipmentId);
		shipment = shipmentRepo.save(shipment);
		
		return ResponseEntity.ok(shipment);
	}
	
	/**
	 * 
	 * @param shipment
	 * @return
	 */
	@GetMapping(path="/{shipmentId}")
	public ResponseEntity<Product> getShipment(@PathVariable long shipmentId) {
		LOG.debug("Get shipment for id " + shipmentId );

		Product shipment = shipmentRepo.findOne(shipmentId);

		return ResponseEntity.ok(shipment);
	}
	
	/**
	 * 
	 * @param shipment
	 * @return
	 */
	@DeleteMapping(path="/{shipmentId}")
	public ResponseEntity<?> deleteShipment(@PathVariable long shipmentId) {
		LOG.debug("delete shipment for id " + shipmentId );

		shipmentRepo.delete(shipmentId);

		return ResponseEntity.noContent().build();
	}


}
