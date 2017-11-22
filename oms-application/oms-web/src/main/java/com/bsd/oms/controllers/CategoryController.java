package com.bsd.oms.controllers;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.bsd.oms.entity.Product;
import com.bsd.oms.entity.ProductCategory;
import com.bsd.oms.entity.ProductSubCategory;
import com.bsd.oms.service.EntityService;

/**
 * 
 * @author Basavaraj Dhanashetti
 *
 */
@Controller
public class CategoryController {

	private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private EntityService entityService;

	@Value("${oms-rest-url}")
	private String omsRestRootURL;

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/categories")
	public String getCategories(Model model, HttpSession session) {
		LOG.debug("In getCategories");
		ProductCategory[] categoryLst = entityService.getByURL(this.omsRestRootURL + "/categories", ProductCategory[].class);
		if (categoryLst != null) {
			model.addAttribute("categoryList", Arrays.asList(categoryLst));
		} else {
			model.addAttribute("no.result", "Product Categories are not configured.");
		}
		return "categoryList";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/categories/{id}")
	public String getNewCategory(Model model, HttpSession session, @PathVariable long id) {
		LOG.debug("In getNewCategory");

		ProductCategory category = null;
		if (id == 0) {
			category = new ProductCategory();
		} else {
			category = entityService.getByURL(this.omsRestRootURL + "/categories/" + id, ProductCategory.class);
		}
		model.addAttribute("productCategoryForm", category);
		return "categoryAddUpdate";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/categories")
	public String saveCategory(Model model, HttpSession session, @ModelAttribute ProductCategory category) {
		LOG.debug("In saveCategory");
		if (category.getId() == 0) {
			entityService.postByURL(this.omsRestRootURL + "/categories", category, ProductCategory.class);
		} else {
			entityService.putByURL(this.omsRestRootURL + "/categories/" + category.getId(), category);
		}
		return "redirect:/categories";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/categories/{catId}/subcategories")
	public String getSubCategories(Model model, HttpSession session, @PathVariable long catId) {
		LOG.debug("In getSubCategories");
		ProductSubCategory[] categoryLst = entityService.getByURL(this.omsRestRootURL + "/categories/" + catId + "/subcategories",
				ProductSubCategory[].class);
		if (categoryLst != null) {
			model.addAttribute("subCategoryList", Arrays.asList(categoryLst));
		} else {
			model.addAttribute("no.result", "Product Categories are not configured.");
		}
		model.addAttribute("categoryId", catId);
		return "subCategoryList";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/categories/{catId}/subcategories/{id}")
	public String getViewSubCategory(Model model, HttpSession session, @PathVariable long id, @PathVariable long catId) {
		LOG.debug("In getViewSubCategory");
		ProductSubCategory subCategory = null;
		if (id == 0) {
			subCategory = new ProductSubCategory();
			subCategory.setProductCategoryId(catId);
		} else {
			subCategory = entityService.getByURL(this.omsRestRootURL + "/categories/" + catId + "/subcategories/" + id,
					ProductSubCategory.class);
		}
		model.addAttribute("productSubCategoryForm", subCategory);
		return "subCategoryAddUpdate";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/categories/{catId}/subcategories")
	public String saveSubCategory(Model model, HttpSession session, @ModelAttribute ProductSubCategory subCategory) {
		LOG.debug("In saveSubCategory");

		if (subCategory.getId() == 0) {
			entityService.postByURL(this.omsRestRootURL + "/categories/" + subCategory.getProductCategoryId() + "/subcategories/",
					subCategory, ProductCategory.class);
		} else {
			entityService.putByURL(this.omsRestRootURL + "/categories/" + subCategory.getProductCategoryId() + "/subcategories/"
					+ subCategory.getId(), subCategory);
		}
		return "redirect:/categories/" + subCategory.getProductCategoryId() + "/subcategories";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/categories/{catId}/subcategories/{subCatId}/products")
	public String getProducts(Model model, HttpSession session, @PathVariable long catId, @PathVariable long subCatId) {
		LOG.debug("In getSubCategories");
		Product[] categoryLst = entityService.getByURL(this.omsRestRootURL + "/categories/" + catId + "/subcategories/" + subCatId
				+ "/products", Product[].class);
		if (categoryLst != null) {
			model.addAttribute("productList", Arrays.asList(categoryLst));

		} else {
			model.addAttribute("no.result", "Products  are not configured.");
		}
		model.addAttribute("categoryId", catId);
		model.addAttribute("subCategoryId", subCatId);
		return "productList";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/categories/{catId}/subcategories/{subCatId}/products/{id}")
	public String getViewProduct(Model model, HttpSession session, @PathVariable long id, @PathVariable long catId,
			@PathVariable long subCatId) {
		LOG.debug("In getViewSubCategory");
		Product product = null;
		if (id == 0) {
			product = new Product();
			product.setProductSubCategoryId(subCatId);
		} else {
			product = entityService.getByURL(this.omsRestRootURL + "/categories/" + catId + "/subcategories/" + subCatId + "/products/"
					+ id, Product.class);
		}
		model.addAttribute("productForm", product);
		model.addAttribute("categoryId", catId);
		return "productAddUpdate";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/categories/{catId}/subcategories/{subCatId}/products")
	public String saveProduct(Model model, HttpSession session, @ModelAttribute Product product, @PathVariable long catId,
			@PathVariable long subCatId) {
		LOG.debug("In saveProduct");

		ProductSubCategory subCategory = entityService.getByURL(
				this.omsRestRootURL + "/categories/" + catId + "/subcategories/" + subCatId, ProductSubCategory.class);

		if (product.getId() == 0) {
			entityService.postByURL(this.omsRestRootURL + "/categories/" + subCategory.getProductCategoryId() + "/subcategories/"
					+ subCategory.getId() + "/products", product, ProductCategory.class);
		} else {
			entityService.putByURL(this.omsRestRootURL + "/categories/" + subCategory.getProductCategoryId() + "/subcategories/"
					+ subCategory.getId() + "/products/" + product.getId(), product);
		}
		return "redirect:/categories/" + subCategory.getProductCategoryId() + "/subcategories/" + subCategory.getId() + "/products";
	}
}
