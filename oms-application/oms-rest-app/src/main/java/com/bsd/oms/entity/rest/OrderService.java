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

import com.bsd.oms.entity.Order;
import com.bsd.oms.repo.OrderRepository;

@RestController
@RequestMapping(path = "/orders")
public class OrderService {

	private static Logger LOG = LoggerFactory.getLogger(OrderService.class);
	
	@Autowired
	private OrderRepository orderRepo; 

	/**
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createOrder(@RequestBody Order order) {
		LOG.debug("Create new order with (" + order.toString() + " )");
		System.out.println("Create new order with (" + order.toString() + " )");
		order = orderRepo.save(order);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(order.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	/**
	 * 
	 * @param order
	 * @return
	 */
	@PutMapping(path="/{orderId}",consumes = "application/json")
	public ResponseEntity<Order> updateOrder(@PathVariable long orderId,@RequestBody Order order) {
		LOG.debug("Update order with (" + order.toString() + " )");
			
		order.setId(orderId);
		order = orderRepo.save(order);
		
		return ResponseEntity.ok(order);
	}
	
	/**
	 * 
	 * @param order
	 * @return
	 */
	@GetMapping(path="/{orderId}")
	public ResponseEntity<Order> getOrder(@PathVariable long orderId) {
		LOG.debug("Get order for id " + orderId );

		Order order = orderRepo.findOne(orderId);

		return ResponseEntity.ok(order);
	}
	
	/**
	 * 
	 * @param order
	 * @return
	 */
	@DeleteMapping(path="/{orderId}")
	public ResponseEntity<?> deleteOrder(@PathVariable long orderId) {
		LOG.debug("delete order for id " + orderId );

		orderRepo.delete(orderId);

		return ResponseEntity.noContent().build();
	}


}
