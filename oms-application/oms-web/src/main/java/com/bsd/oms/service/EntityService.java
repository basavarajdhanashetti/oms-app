package com.bsd.oms.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.bsd.oms.controllers.PurchaseRequestController;
import com.bsd.oms.entity.ApprovalDetails;
import com.bsd.oms.entity.Department;
import com.bsd.oms.entity.PurchaseOrder;
import com.bsd.oms.entity.PurchaseRequest;
import com.bsd.oms.entity.Quotation;
import com.bsd.oms.process.Product;
import com.bsd.oms.process.ProductCategory;
import com.bsd.oms.process.Vendor;

@Component
public class EntityService {

	private static final Logger LOG = LoggerFactory.getLogger(PurchaseRequestController.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${oms-rest-url}")
	private String omsRestRootURL;

	@Value("${oms-process-url}")
	private String omsProcessRootURL;

	/**
	 * 
	 * @return
	 */
	public List<Department> getDepartments() {
		ResponseEntity<Department[]> categoryResp = restTemplate.getForEntity(this.omsRestRootURL + "/departments", Department[].class);

		if (categoryResp.getStatusCode() == HttpStatus.OK) {
			return Arrays.asList(categoryResp.getBody());
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @return
	 */
	public Department getDepartmentById(long id) {
		ResponseEntity<Department> categoryResp = restTemplate.getForEntity(this.omsRestRootURL + "/departments/" + id, Department.class);

		if (categoryResp.getStatusCode() == HttpStatus.OK) {
			return categoryResp.getBody();
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @return
	 * @return
	 */
	public List<ProductCategory> getCategories() {
		ResponseEntity<ProductCategory[]> categoryResp = restTemplate.getForEntity(this.omsRestRootURL + "/categories",
				ProductCategory[].class);

		if (categoryResp.getStatusCode() == HttpStatus.OK) {
			return Arrays.asList(categoryResp.getBody());
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @return
	 */
	public List<Vendor> getVendors() {
		ResponseEntity<Vendor[]> categoryResp = restTemplate.getForEntity(this.omsRestRootURL + "/vendors", Vendor[].class);

		if (categoryResp.getStatusCode() == HttpStatus.OK) {
			return Arrays.asList(categoryResp.getBody());
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @return
	 * @return
	 */
	public Product getProduct(long categoryId, long subCategoryId, long productId) {
		ResponseEntity<Product> categoryResp = restTemplate.getForEntity(this.omsRestRootURL + "/categories/" + categoryId
				+ "/subcategories/" + subCategoryId + "/products/" + productId, Product.class);

		if (categoryResp.getStatusCode() == HttpStatus.OK) {
			return categoryResp.getBody();
		} else {
			LOG.error("Product is not found for id:" + productId);
			return null;
		}
	}

	/**
	 * 
	 * @return
	 * @return
	 */
	public Vendor getVendor(long vendorId) {
		ResponseEntity<Vendor> categoryResp = restTemplate.getForEntity(this.omsRestRootURL + "/vendors/" + vendorId, Vendor.class);

		if (categoryResp.getStatusCode() == HttpStatus.OK) {
			return categoryResp.getBody();
		} else {
			LOG.error("Vendor is not found for id:" + vendorId);
			return null;
		}
	}

	/**
	 * 
	 * @return
	 * @return
	 */
	public PurchaseRequest getPurchaseRequest(long purchaseReqId) {
		ResponseEntity<PurchaseRequest> categoryResp = restTemplate.getForEntity(this.omsRestRootURL + "/purchases/" + purchaseReqId,
				PurchaseRequest.class);

		if (categoryResp.getStatusCode() == HttpStatus.OK) {
			return categoryResp.getBody();
		} else {
			LOG.error("PurchaseRequest is not found for id:" + purchaseReqId);
			return null;
		}
	}

	/**
	 * 
	 * @return
	 * @return
	 */
	public List<Quotation> getQuotationsByPurchaseRequest(long purchaseReqId) {
		ResponseEntity<Quotation[]> categoryResp = restTemplate.getForEntity(this.omsRestRootURL + "/purchases/" + purchaseReqId
				+ "/quotations", Quotation[].class);

		if (categoryResp.getStatusCode() == HttpStatus.OK) {
			return Arrays.asList(categoryResp.getBody());
		} else {
			LOG.error("Quotations for PurchaseRequest is not found for id:" + purchaseReqId);
			return null;
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Quotation getQuotation(long id) {

		ResponseEntity<Quotation> categoryResp = restTemplate.getForEntity(this.omsRestRootURL + "/quotations/" + id, Quotation.class);

		if (categoryResp.getStatusCode() == HttpStatus.OK) {
			return categoryResp.getBody();
		} else {
			LOG.error("Quotation is not found for id:" + id);
			return null;
		}

	}

	/**
	 * 
	 * @param quotation
	 */
	public void updateQuotation(com.bsd.oms.entity.Quotation quotation) {

		restTemplate.put(this.omsRestRootURL + "/quotations/" + quotation.getId(), quotation);

	}

	/**
	 * 
	 * @param purchaseOrderId
	 * @return
	 */
	public PurchaseOrder getPurchaseOrder(long purchaseOrderId) {

		ResponseEntity<PurchaseOrder> categoryResp = restTemplate.getForEntity(this.omsRestRootURL + "/purchaseorders/" + purchaseOrderId,
				PurchaseOrder.class);

		if (categoryResp.getStatusCode() == HttpStatus.OK) {
			return categoryResp.getBody();
		} else {
			LOG.error("PurchaseOrder is not found for id:" + purchaseOrderId);
			return null;
		}
	}

	/**
	 * 
	 * @param quotationId
	 * @return
	 */
	public PurchaseOrder getPurchaseOrderByQuotation(long quotationId) {

		ResponseEntity<PurchaseOrder> categoryResp = restTemplate.getForEntity(this.omsRestRootURL + "/quotations/"+quotationId+"/purchaseorder",
				PurchaseOrder.class);

		if (categoryResp.getStatusCode() == HttpStatus.OK) {
			return categoryResp.getBody();
		} else {
			LOG.error("PurchaseOrder is not found for id:" + quotationId);
			return null;
		}
	}

	/**
	 * 
	 * @param approvalDetails
	 */
	public void postApprovalData(ApprovalDetails approvalDetails) {
		ResponseEntity<String> approvalDAtaResp = restTemplate.postForEntity(this.omsRestRootURL + "/quotations/", 
				approvalDetails, String.class);

		if (approvalDAtaResp.getStatusCode() == HttpStatus.CREATED) {
			LOG.error("Added entry to Approval Details");
		} else {
			LOG.error("Unable to add entry to Approval Details");
		}
	}
}
