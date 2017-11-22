package com.bsd.oms.rest;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.bsd.oms.entity.Product;
import com.bsd.oms.entity.ProductCategory;
import com.bsd.oms.entity.ProductSubCategory;

@Controller
@RequestMapping("/api/categories")
public class ProductHeirachyService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${oms-rest-url}")
	private String omsRestRootURL;
	
	/**
	 * 
	 * @return
	 */
	@GetMapping
	public @ResponseBody ResponseEntity<?> getAllCateogies(){
		
		ResponseEntity<ProductCategory[]> categoryResp = restTemplate.getForEntity(
				this.omsRestRootURL + "/categories", ProductCategory[].class);
		
		if (categoryResp.getStatusCode() == HttpStatus.OK) {
			return ResponseEntity.ok(Arrays.asList(categoryResp.getBody()));
		} else {
			return ResponseEntity.status(500).build();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@GetMapping(path="{categoryId}/subcategories")
	public @ResponseBody ResponseEntity<?> getAllSubCateogies(@PathVariable long categoryId){
		
		ResponseEntity<ProductSubCategory[]> categoryResp = restTemplate.getForEntity(
				this.omsRestRootURL + "/categories/"+categoryId+"/subcategories", ProductSubCategory[].class);
		
		if (categoryResp.getStatusCode() == HttpStatus.OK) {
			return ResponseEntity.ok(Arrays.asList(categoryResp.getBody()));
		} else {
			return ResponseEntity.status(500).build();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@GetMapping(path="{categoryId}/subcategories/{subCategoryId}/products")
	public @ResponseBody ResponseEntity<?> getAllProducts(@PathVariable long categoryId, @PathVariable long subCategoryId){
		
		ResponseEntity<Product[]> categoryResp = restTemplate.getForEntity(
				this.omsRestRootURL + "/categories/"+categoryId+"/subcategories/"+subCategoryId+"/products", Product[].class);
		
		if (categoryResp.getStatusCode() == HttpStatus.OK) {
			return ResponseEntity.ok(Arrays.asList(categoryResp.getBody()));
		} else {
			return ResponseEntity.status(500).build();
		}
	}
}
