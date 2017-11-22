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

import com.bsd.oms.entity.Product;
import com.bsd.oms.entity.ProductCategory;
import com.bsd.oms.entity.ProductSubCategory;
import com.bsd.oms.repo.ProductCategoryRepository;
import com.bsd.oms.repo.ProductRepository;
import com.bsd.oms.repo.ProductSubCategoryRepository;

@RestController
@RequestMapping(path = "/categories")
public class ProductCategoryService {

	private static Logger LOG = LoggerFactory.getLogger(ProductCategoryService.class);

	@Autowired
	private ProductCategoryRepository productCategoryRepo;

	@Autowired
	private ProductSubCategoryRepository productSubCategoryRepo;

	@Autowired
	private ProductRepository productRepo;

	/**
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createProductCategory(@RequestBody ProductCategory productCategory) {
		LOG.debug("Create new productCategory with (" + productCategory.toString() + " )");
		productCategory = productCategoryRepo.save(productCategory);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productCategory.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * 
	 * @param order
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<List<ProductCategory>> getAllProductCategory() {
		LOG.debug("Get All productCategory ");
		List<ProductCategory> lst = new ArrayList<ProductCategory>();
		for (ProductCategory productCategory : productCategoryRepo.findAll()) {
			lst.add(productCategory);
		}
		return ResponseEntity.ok(lst);
	}

	/**
	 * 
	 * @param productCategory
	 * @return
	 */
	@PutMapping(path = "/{productCategoryId}", consumes = "application/json")
	public ResponseEntity<ProductCategory> updateProductCategory(@PathVariable long productCategoryId,
			@RequestBody ProductCategory productCategory) {
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
	@GetMapping(path = "/{productCategoryId}")
	public ResponseEntity<ProductCategory> getProductCategory(@PathVariable long productCategoryId) {
		LOG.debug("Get productCategory for id " + productCategoryId);

		ProductCategory productCategory = productCategoryRepo.findOne(productCategoryId);

		return ResponseEntity.ok(productCategory);
	}

	/**
	 * 
	 * @param productCategory
	 * @return
	 */
	@DeleteMapping(path = "/{productCategoryId}")
	public ResponseEntity<?> deleteProductCategory(@PathVariable long productCategoryId) {
		LOG.debug("delete productCategory for id " + productCategoryId);

		productCategoryRepo.delete(productCategoryId);

		return ResponseEntity.noContent().build();
	}

	/************************************************************************************/
	/**
	 * 
	 * @param order
	 * @return
	 */
	@GetMapping(path = "/{productCategoryId}/subcategories")
	public ResponseEntity<List<ProductSubCategory>> getAllProductSubCategory(@PathVariable long productCategoryId) {
		LOG.debug("Get All productSubCategory ");
		return ResponseEntity.ok(productSubCategoryRepo.getByProductCategoryId(productCategoryId));
	}

	/**
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(path = "/{productCategoryId}/subcategories", consumes = "application/json")
	public ResponseEntity<?> createProductSubCategory(@PathVariable long productCategoryId, @RequestBody ProductSubCategory productSubCategory) {
		LOG.debug("Create new productSubCategory with (" + productSubCategory.toString() + " )");
		productSubCategory.setProductCategoryId(productCategoryId);
		productSubCategory = productSubCategoryRepo.save(productSubCategory);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productSubCategory.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * 
	 * @param productSubCategory
	 * @return
	 */
	@PutMapping(path = "/{productCategoryId}/subcategories/{productSubCategoryId}", consumes = "application/json")
	public ResponseEntity<ProductSubCategory> updateProductSubCategory(@PathVariable long productCategoryId,
			@PathVariable long productSubCategoryId, @RequestBody ProductSubCategory productSubCategory) {
		LOG.debug("Update productSubCategory with (" + productSubCategory.toString() + " )");

		productSubCategory.setId(productSubCategoryId);
		productSubCategory.setProductCategoryId(productCategoryId);
		productSubCategory = productSubCategoryRepo.save(productSubCategory);

		return ResponseEntity.ok(productSubCategory);
	}

	/**
	 * 
	 * @param productSubCategory
	 * @return
	 */
	@GetMapping(path = "/{productCategoryId}/subcategories/{productSubCategoryId}")
	public ResponseEntity<ProductSubCategory> getProductSubCategory(@PathVariable long productCategoryId,
			@PathVariable long productSubCategoryId) {
		LOG.debug("Get productSubCategory for id " + productSubCategoryId);

		ProductSubCategory productSubCategory = productSubCategoryRepo.findOne(productSubCategoryId);

		return ResponseEntity.ok(productSubCategory);
	}

	/**
	 * 
	 * @param productSubCategory
	 * @return
	 */
	@DeleteMapping(path = "/{productCategoryId}/subcategories/{productSubCategoryId}")
	public ResponseEntity<?> deleteProductSubCategory(@PathVariable long productCategoryId, @PathVariable long productSubCategoryId) {
		LOG.debug("delete productSubCategory for id " + productSubCategoryId);

		productSubCategoryRepo.delete(productSubCategoryId);

		return ResponseEntity.noContent().build();
	}

	/*******************************************************************************/

	/**
	 * 
	 * @param Product
	 * @return
	 */
	@GetMapping(path = "/{productCategoryId}/subcategories/{productSubCategoryId}/products")
	public ResponseEntity<List<Product>> getProducts(@PathVariable long productCategoryId, @PathVariable long productSubCategoryId) {
		LOG.debug("Get All product ");
		return ResponseEntity.ok(productRepo.getByProductSubCategoryId(productSubCategoryId));
	}

	/**
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(path = "/{productCategoryId}/subcategories/{productSubCategoryId}/products", consumes = "application/json")
	public ResponseEntity<?> createProductCategory(@PathVariable long productCategoryId, @PathVariable long productSubCategoryId,
			@RequestBody Product product) {
		LOG.debug("Create new product with (" + product.toString() + " )");
		product.setProductSubCategoryId(productSubCategoryId);
		product = productRepo.save(product);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * 
	 * @param product
	 * @return
	 */
	@PutMapping(path = "/{productCategoryId}/subcategories/{productSubCategoryId}/products/{productId}", consumes = "application/json")
	public ResponseEntity<Product> updateProduct(@PathVariable long productCategoryId, @PathVariable long productSubCategoryId,
			@PathVariable long productId, @RequestBody Product product) {
		LOG.debug("Update product with (" + product.toString() + " )");

		product.setId(productId);
		product.setProductSubCategoryId(productSubCategoryId);
		product = productRepo.save(product);

		return ResponseEntity.ok(product);
	}

	/**
	 * 
	 * @param product
	 * @return
	 */
	@GetMapping(path = "/{productCategoryId}/subcategories/{productSubCategoryId}/products/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable long productCategoryId, @PathVariable long productSubCategoryId,
			@PathVariable long productId) {
		LOG.debug("Get product for id " + productId);

		Product product = productRepo.findOne(productId);

		return ResponseEntity.ok(product);
	}

	/**
	 * 
	 * @param product
	 * @return
	 */
	@DeleteMapping(path = "/{productCategoryId}/subcategories/{productSubCategoryId}/products/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable long productCategoryId, @PathVariable long productSubCategoryId,
			@PathVariable long productId) {
		LOG.debug("delete product for id " + productId);

		productRepo.delete(productId);

		return ResponseEntity.noContent().build();
	}

}
