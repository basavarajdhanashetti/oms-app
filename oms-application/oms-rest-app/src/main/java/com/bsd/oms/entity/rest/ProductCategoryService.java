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

import com.bsd.oms.entity.ProductCategory;
import com.bsd.oms.repo.ProductCategoryRepository;

@RestController
@RequestMapping(path = "/categories")
@Api(tags =  {"Entity", "Product Category"})
public class ProductCategoryService {


	private static Logger LOG = LoggerFactory.getLogger(ProductCategoryService.class);
	
	@Autowired
	private ProductCategoryRepository productCategoryRepo; 

	/**
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createProductCategory(@RequestBody ProductCategory productCategory) {
		LOG.debug("Create new productCategory with (" + productCategory.toString() + " )");
		System.out.println("Create new productCategory with (" + productCategory.toString() + " )");
		productCategory = productCategoryRepo.save(productCategory);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(productCategory.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	/**
	 * 
	 * @param productCategory
	 * @return
	 */
	@PutMapping(path="/{productCategoryId}",consumes = "application/json")
	public ResponseEntity<ProductCategory> updateProductCategory(@PathVariable long productCategoryId,@RequestBody ProductCategory productCategory) {
		LOG.debug("Update productCategory with (" + productCategory.toString() + " )");
			
		productCategory.setId(productCategoryId);
		productCategory = productCategoryRepo.save(productCategory);
		
		return ResponseEntity.ok(productCategory);
	}
	
	/**
	 * 
	 * @param productCategory
	 * @return
	 */
	@GetMapping(path="/{productCategoryId}")
	public ResponseEntity<ProductCategory> getProductCategory(@PathVariable long productCategoryId) {
		LOG.debug("Get productCategory for id " + productCategoryId );

		ProductCategory productCategory = productCategoryRepo.findOne(productCategoryId);

		return ResponseEntity.ok(productCategory);
	}
	
	/**
	 * 
	 * @param productCategory
	 * @return
	 */
	@DeleteMapping(path="/{productCategoryId}")
	public ResponseEntity<?> deleteProductCategory(@PathVariable long productCategoryId) {
		LOG.debug("delete productCategory for id " + productCategoryId );

		productCategoryRepo.delete(productCategoryId);

		return ResponseEntity.noContent().build();
	}


}
