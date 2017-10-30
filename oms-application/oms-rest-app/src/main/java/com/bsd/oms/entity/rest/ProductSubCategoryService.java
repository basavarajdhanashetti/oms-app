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

import com.bsd.oms.entity.ProductSubCategory;
import com.bsd.oms.repo.ProductSubCategoryRepository;


@RestController
@RequestMapping(path = "/subcategories")
@Api(tags =  {"Entity", "Product Sub Category"})
public class ProductSubCategoryService {


	private static Logger LOG = LoggerFactory.getLogger(ProductCategoryService.class);
	
	@Autowired
	private ProductSubCategoryRepository productSubCategoryRepo; 

	/**
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createProductCategory(@RequestBody ProductSubCategory productSubCategory) {
		LOG.debug("Create new productSubCategory with (" + productSubCategory.toString() + " )");
		System.out.println("Create new productSubCategory with (" + productSubCategory.toString() + " )");
		productSubCategory = productSubCategoryRepo.save(productSubCategory);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(productSubCategory.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	/**
	 * 
	 * @param productSubCategory
	 * @return
	 */
	@PutMapping(path="/{productCategoryId}",consumes = "application/json")
	public ResponseEntity<ProductSubCategory> updateProductCategory(@PathVariable long productCategoryId,@RequestBody ProductSubCategory productSubCategory) {
		LOG.debug("Update productSubCategory with (" + productSubCategory.toString() + " )");
			
		productSubCategory.setId(productCategoryId);
		productSubCategory = productSubCategoryRepo.save(productSubCategory);
		
		return ResponseEntity.ok(productSubCategory);
	}
	
	/**
	 * 
	 * @param productSubCategory
	 * @return
	 */
	@GetMapping(path="/{productCategoryId}")
	public ResponseEntity<ProductSubCategory> getProductCategory(@PathVariable long productCategoryId) {
		LOG.debug("Get productSubCategory for id " + productCategoryId );

		ProductSubCategory productSubCategory = productSubCategoryRepo.findOne(productCategoryId);

		return ResponseEntity.ok(productSubCategory);
	}
	
	/**
	 * 
	 * @param productSubCategory
	 * @return
	 */
	@DeleteMapping(path="/{productCategoryId}")
	public ResponseEntity<?> deleteProductCategory(@PathVariable long productCategoryId) {
		LOG.debug("delete productSubCategory for id " + productCategoryId );

		productSubCategoryRepo.delete(productCategoryId);

		return ResponseEntity.noContent().build();
	}


}
