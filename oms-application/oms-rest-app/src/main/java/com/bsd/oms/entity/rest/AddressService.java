package com.bsd.oms.entity.rest;

import io.swagger.annotations.Api;

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

import com.bsd.oms.entity.Address;
import com.bsd.oms.repo.AddressRepository;


@RestController
@RequestMapping(path = "/addresses")
@Api(tags =  {"Entity", "Address"})
public class AddressService {


	private static Logger LOG = LoggerFactory.getLogger(AddressService.class);
	
	@Autowired
	private AddressRepository addressRepo; 

	/**
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createAddress(@RequestBody Address address) {
		LOG.debug("Create new address with (" + address.toString() + " )");
		System.out.println("Create new address with (" + address.toString() + " )");
		address = addressRepo.save(address);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(address.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	/**
	 * 
	 * @param address
	 * @return
	 */
	@PutMapping(path="/{addressId}",consumes = "application/json")
	public ResponseEntity<Address> updateAddress(@PathVariable long addressId,@RequestBody Address address) {
		LOG.debug("Update address with (" + address.toString() + " )");
			
		address.setId(addressId);
		address = addressRepo.save(address);
		
		return ResponseEntity.ok(address);
	}
	
	/**
	 * 
	 * @param address
	 * @return
	 */
	@GetMapping(path="/{addressId}")
	public ResponseEntity<Address> getAddress(@PathVariable long addressId) {
		LOG.debug("Get address for id " + addressId );

		Address address = addressRepo.findOne(addressId);

		return ResponseEntity.ok(address);
	}
	
	/**
	 * 
	 * @param address
	 * @return
	 */
	@DeleteMapping(path="/{addressId}")
	public ResponseEntity<?> deleteAddress(@PathVariable long addressId) {
		LOG.debug("delete address for id " + addressId );

		addressRepo.delete(addressId);

		return ResponseEntity.noContent().build();
	}


}
