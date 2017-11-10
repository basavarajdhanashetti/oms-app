package com.bsd.oms.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="quotations")
public class Quotation {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="id_purchase_request")
	private long idPurchaseRequest;
	
	@ManyToOne
	@JoinColumn(name="id_purchase_request", insertable=false, updatable=false)
	private PurchaseRequest purchaseRequest;
	
	@Column(name="id_vendor")
	private long idVendor;
	
	@ManyToOne
	@JoinColumn(name="id_vendor", insertable=false, updatable=false)
	private Vendor vendor;
	
	private double quoteAmount;
	
	private double tax;
	
	private double totalAmount;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idQuotation")
	private List<QuotationItem> items;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceId")
	private List<ApprovalDetails> approvalList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdPurchaseRequest() {
		return idPurchaseRequest;
	}

	public void setIdPurchaseRequest(long idPurchaseRequest) {
		this.idPurchaseRequest = idPurchaseRequest;
	}

	public PurchaseRequest getPurchaseRequest() {
		return purchaseRequest;
	}

	public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
		this.purchaseRequest = purchaseRequest;
	}

	public long getIdVendor() {
		return idVendor;
	}

	public void setIdVendor(long idVendor) {
		this.idVendor = idVendor;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public double getQuoteAmount() {
		return quoteAmount;
	}

	public void setQuoteAmount(double quoteAmount) {
		this.quoteAmount = quoteAmount;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<QuotationItem> getItems() {
		return items;
	}

	public void setItems(List<QuotationItem> items) {
		this.items = items;
	}

	public List<ApprovalDetails> getApprovalList() {
		return approvalList;
	}

	public void setApprovalList(List<ApprovalDetails> approvalList) {
		this.approvalList = approvalList;
	}
	
}
