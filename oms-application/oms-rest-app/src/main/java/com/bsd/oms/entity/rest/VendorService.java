package com.bsd.oms.entity.rest;

import java.net.URI;
import java.util.ArrayList;
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

import com.bsd.oms.entity.Vendor;
import com.bsd.oms.repo.VendorRepository;

@RestController
@RequestMapping(path = "/vendors")
public class VendorService {

	private static Logger LOG = LoggerFactory.getLogger(VendorService.class);

	@Autowired
	private VendorRepository vendorRepo;

	/**
	 * 
	 * @param order
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<List<Vendor>> getAllVendor() {
		LOG.debug("Get All vendor ");
		System.out.println("Get All vendor ");
		List<Vendor> lst = new ArrayList<Vendor>();
		for (Vendor vendor : vendorRepo.findAll()) {
			lst.add(vendor);
		}
		return ResponseEntity.ok(lst);
	}

	/**
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createVendor(@RequestBody Vendor vendor) {
		LOG.debug("Create new vendor with (" + vendor.toString() + " )");
		System.out.println("Create new vendor with (" + vendor.toString() + " )");
		vendor = vendorRepo.save(vendor);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(vendor.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * 
	 * @param vendor
	 * @return
	 */
	@PutMapping(path = "/{vendorId}", consumes = "application/json")
	public ResponseEntity<Vendor> updateVendor(@PathVariable long vendorId, @RequestBody Vendor vendor) {
		LOG.debug("Update vendor with (" + vendor.toString() + " )");

		vendor.setId(vendorId);
		vendor = vendorRepo.save(vendor);

		return ResponseEntity.ok(vendor);
	}

	/**
	 * 
	 * @param vendor
	 * @return
	 */
	@GetMapping(path = "/{vendorId}")
	public ResponseEntity<Vendor> getVendor(@PathVariable long vendorId) {
		LOG.debug("Get vendor for id " + vendorId);

		Vendor vendor = vendorRepo.findOne(vendorId);

		return ResponseEntity.ok(vendor);
	}

	/**
	 * 
	 * @param vendor
	 * @return
	 */
	@DeleteMapping(path = "/{vendorId}")
	public ResponseEntity<?> deleteVendor(@PathVariable long vendorId) {
		LOG.debug("delete vendor for id " + vendorId);

		vendorRepo.delete(vendorId);

		return ResponseEntity.noContent().build();
	}

}
