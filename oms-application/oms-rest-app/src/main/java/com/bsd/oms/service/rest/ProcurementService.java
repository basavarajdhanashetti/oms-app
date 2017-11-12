package com.bsd.oms.service.rest;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bsd.oms.entity.ApprovalDetails;
import com.bsd.oms.entity.ApprovalDetails.ApprovalStatusType;
import com.bsd.oms.entity.ApprovalDetails.MaterialType;
import com.bsd.oms.entity.PurchaseItem;
import com.bsd.oms.entity.PurchaseOrder;
import com.bsd.oms.entity.PurchaseOrderItem;
import com.bsd.oms.entity.PurchaseRequest;
import com.bsd.oms.entity.Quotation;
import com.bsd.oms.entity.QuotationItem;
import com.bsd.oms.entity.rest.PurchaseRequestService;
import com.bsd.oms.repo.PurchaseOrderRepository;
import com.bsd.oms.repo.PurchaseRequestRepository;
import com.bsd.oms.repo.QuotationRepository;
import com.bsd.oms.utils.OMSDateUtil;

@RestController
@RequestMapping(path = "/procurements")
public class ProcurementService {

	private static Logger LOG = LoggerFactory.getLogger(PurchaseRequestService.class);

	@Autowired
	private PurchaseRequestRepository purchaseRequestRepo;

	@Autowired
	private QuotationRepository quotationRepo;
	
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepo;
	

	/**
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(path = "/purchases", consumes = "application/json")
	public ResponseEntity<com.bsd.oms.process.PurchaseRequest> createPurchaseRequest(@RequestBody com.bsd.oms.process.PurchaseRequest purchaseRequest) {
		LOG.debug("Create new purchaseRequest with (" + purchaseRequest.toString() + " )");
		System.out.println("Create new purchaseRequest with (" + purchaseRequest.toString() + " )");

		PurchaseRequest prEntity = purchaseRequestMapping(purchaseRequest);

		purchaseRequest.setId(prEntity.getId());
		return ResponseEntity.ok(purchaseRequest);
	}

	/**
	 * 
	 * @param purchaseRequest
	 * @return
	 */
	private PurchaseRequest purchaseRequestMapping(com.bsd.oms.process.PurchaseRequest purchaseRequest) {

		// Set Parent table details
		PurchaseRequest prEntity = new PurchaseRequest();
		prEntity.setDepartment(purchaseRequest.getDepartment());
		prEntity.setRequestDate(OMSDateUtil.toDate(purchaseRequest.getRequestDate()));
		prEntity.setRequestedBy(purchaseRequest.getRequestedBy());
		prEntity.setRequestNo(purchaseRequest.getRequestNo());

		prEntity = purchaseRequestRepo.save(prEntity);

		prEntity.setApprovalList(getApprovalDetails(purchaseRequest.getApprover().getUserId(), purchaseRequest.getApprover()
				.getCreatedDate(), prEntity.getId(), MaterialType.PurchaseOrder, ApprovalStatusType.Submited));

		// Set purchase items
		List<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>();
		for (com.bsd.oms.process.PurchaseItem pItem : purchaseRequest.getItems()) {
			PurchaseItem item = new PurchaseItem();

			item.setIdPurchseRequest(prEntity.getId());
			item.setIdProduct(pItem.getProductId());
			item.setQuantity(pItem.getQuantity());
			item.setDescription(pItem.getDescription());

			purchaseItems.add(item);
		}
		prEntity.setItems(purchaseItems);

		prEntity = purchaseRequestRepo.save(prEntity);

		return prEntity;
	}

	/**
	 * 
	 * @param userId
	 * @param createdDate
	 * @param referenceId
	 * @param materialType
	 * @param approvalStatusType
	 * @return
	 */
	private List<ApprovalDetails> getApprovalDetails(String userId, String createdDate, long referenceId, MaterialType materialType,
			ApprovalStatusType approvalStatusType) {
		// Set Approval Details
		List<ApprovalDetails> approvalList = new ArrayList<ApprovalDetails>();
		ApprovalDetails apprDetails = new ApprovalDetails();
		apprDetails.setUserId(userId);
		apprDetails.setCreatedDate(OMSDateUtil.toDate(createdDate));
		apprDetails.setMaterial(materialType);
		apprDetails.setStatus(approvalStatusType);
		apprDetails.setReferenceId(referenceId);
		approvalList.add(apprDetails);
		return approvalList;
	}

	/**
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(path = "/purchases/{purchaseRequestId}/quotations", consumes = "application/json")
	public ResponseEntity<?> createPurchaseRequest(@PathVariable long purchaseRequestId,
			@RequestBody com.bsd.oms.process.PRQuotations prQuotations) {
		LOG.debug("Create new Quotations with (" + prQuotations.toString() + " )");
		System.out.println("Create new purchaseRequest with (" + prQuotations.toString() + " )");

		for (com.bsd.oms.process.Quotation quot : prQuotations.getQuotations()) {

			Quotation quotation = new Quotation();

			quotation.setIdPurchaseRequest(purchaseRequestId);

			quotation.setIdVendor(quot.getVendor().getId());

			quotation.setQuoteAmount(quot.getQuoteAmount());

			quotation.setTax(quot.getTax());

			quotation.setTotalAmount(quot.getTotalAmount());

			quotation = quotationRepo.save(quotation);
			
			//Setting Quotation Items
			List<QuotationItem> items = new ArrayList<QuotationItem>();
			for (com.bsd.oms.process.PurchaseItem pItem : quot.getItems()) {
				QuotationItem item = new QuotationItem();

				item.setIdQuotation(quotation.getId());

				item.setIdProduct(pItem.getProductId());

				item.setQuantity(pItem.getQuantity());

				item.setDescription(pItem.getDescription());

				item.setUnitPrice(pItem.getUnitPrice());

				item.setDiscount(pItem.getDiscount());

				item.setSalePrice(pItem.getSalePrice());

				items.add(item);
			}
			quotation.setItems(items);
			
			//Setting History
			quotation.setApprovalList(getApprovalDetails(prQuotations.getApprover().getUserId(), prQuotations.getApprover()
					.getCreatedDate(), quotation.getId(), MaterialType.Quotation, ApprovalStatusType.Submited));

			quotation = quotationRepo.save(quotation);
		}

		return ResponseEntity.ok().build();
	}


	/**
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(path = "/purchases/{purchaseRequestId}/quotations/{quotationId}/purchaseorders")
	public ResponseEntity<?> createPurchaseOrder(@PathVariable long purchaseRequestId,
			@PathVariable long quotationId, @RequestParam String userId) {
		LOG.debug("Create PO for Quotations id:" + quotationId );
		System.out.println("Create PO for Quotations id:" + quotationId );
		
		Quotation quot = quotationRepo.findOne(quotationId);
		
		PurchaseOrder po = new PurchaseOrder();
		po.setIdQuotation(quot.getId());
		po.setIdVendor(quot.getIdVendor());
		po.setPoAmount(quot.getQuoteAmount());
		po.setTax(quot.getTax());
		po.setTotalAmount(quot.getTotalAmount());
		po.setPoDate(new Date());
		po = purchaseOrderRepo.save(po);
		Calendar cal = Calendar.getInstance(); 
		po.setPoNumber("PO-"+ cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1) + "/"+  String.format("%04d", po.getId()));
		List<PurchaseOrderItem> items = new ArrayList<PurchaseOrderItem>();
		
		for (QuotationItem qItem : quot.getItems()) {
			PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
			
			purchaseOrderItem.setIdPurchaseOrder(po.getId());;
			purchaseOrderItem.setIdProduct(qItem.getIdProduct());
			purchaseOrderItem.setQuantity(qItem.getQuantity());
			purchaseOrderItem.setDescription(qItem.getDescription());
			purchaseOrderItem.setUnitPrice(qItem.getUnitPrice());
			purchaseOrderItem.setDiscount(qItem.getDiscount());
			purchaseOrderItem.setSalePrice(qItem.getSalePrice());
			
			items.add(purchaseOrderItem);
		}
		
		po.setItems(items);
		
		List<ApprovalDetails> approvalList = getApprovalDetails(userId, OMSDateUtil.getCurrentDate(), po.getId(), MaterialType.PurchaseOrder, ApprovalStatusType.Approved);
		
		po.setApprovalList(approvalList);
		
		po = purchaseOrderRepo.save(po);
		
		return ResponseEntity.ok().build();
	}
}
