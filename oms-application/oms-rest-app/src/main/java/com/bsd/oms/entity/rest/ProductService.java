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

import com.bsd.oms.entity.Product;
import com.bsd.oms.repo.ProductRepository;


@RestController
@RequestMapping(path = "/products")
@Api(tags =  {"Entity", "Product"})
public class ProductService {


	private static Logger LOG = LoggerFactory.getLogger(ProductCategoryService.class);
	
	@Autowired
	private ProductRepository productRepo; 

	/**
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createProductCategory(@RequestBody Product product) {
		LOG.debug("Create new product with (" + product.toString() + " )");
		System.out.println("Create new product with (" + product.toString() + " )");
		product = productRepo.save(product);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(product.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	/**
	 * 
	 * @param product
	 * @return
	 */
	@PutMapping(path="/{productId}",consumes = "application/json")
	public ResponseEntity<Product> updateProductCategory(@PathVariable long productId,@RequestBody Product product) {
		LOG.debug("Update product with (" + product.toString() + " )");
			
		product.setId(productId);
		product = productRepo.save(product);
		
		return ResponseEntity.ok(product);
	}
	
	/**
	 * 
	 * @param product
	 * @return
	 */
	@GetMapping(path="/{productId}")
	public ResponseEntity<Product> getProductCategory(@PathVariable long productId) {
		LOG.debug("Get product for id " + productId );

		Product product = productRepo.findOne(productId);

		return ResponseEntity.ok(product);
	}
	
	/**
	 * 
	 * @param product
	 * @return
	 */
	@DeleteMapping(path="/{productId}")
	public ResponseEntity<?> deleteProductCategory(@PathVariable long productId) {
		LOG.debug("delete product for id " + productId );

		productRepo.delete(productId);

		return ResponseEntity.noContent().build();
	}


}
