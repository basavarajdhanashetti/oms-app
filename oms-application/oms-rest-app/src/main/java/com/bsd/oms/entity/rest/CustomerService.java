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

import com.bsd.oms.entity.Customer;
import com.bsd.oms.repo.CustomerRepository;


@RestController
@RequestMapping(path = "/customers")
public class CustomerService {

	private static Logger LOG = LoggerFactory.getLogger(CustomerService.class);
	
	@Autowired
	private CustomerRepository customerRepo; 

	/**
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
		LOG.debug("Create new customer with (" + customer.toString() + " )");
		System.out.println("Create new customer with (" + customer.toString() + " )");
		customer = customerRepo.save(customer);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(customer.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	/**
	 * 
	 * @param customer
	 * @return
	 */
	@PutMapping(path="/{customerId}",consumes = "application/json")
	public ResponseEntity<Customer> updateCustomer(@PathVariable long customerId,@RequestBody Customer customer) {
		LOG.debug("Update customer with (" + customer.toString() + " )");
			
		customer.setId(customerId);
		customer = customerRepo.save(customer);
		
		return ResponseEntity.ok(customer);
	}
	
	/**
	 * 
	 * @param customer
	 * @return
	 */
	@GetMapping(path="/{customerId}")
	public ResponseEntity<Customer> getCustomer(@PathVariable long customerId) {
		LOG.debug("Get customer for id " + customerId );

		Customer customer = customerRepo.findOne(customerId);

		return ResponseEntity.ok(customer);
	}
	
	/**
	 * 
	 * @param customer
	 * @return
	 */
	@DeleteMapping(path="/{customerId}")
	public ResponseEntity<?> deleteCustomer(@PathVariable long customerId) {
		LOG.debug("delete customer for id " + customerId );

		customerRepo.delete(customerId);

		return ResponseEntity.noContent().build();
	}

}
