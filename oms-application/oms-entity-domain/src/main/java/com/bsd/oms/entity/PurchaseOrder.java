package com.bsd.oms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="purchase_orders")
@NamedQueries({ @NamedQuery(name = "PurchaseOrder.getAllBetweenDates", query = "SELECT po FROM PurchaseOrder po where po.poDate between :fromDate and :toDate ")
	})
public class PurchaseOrder {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="id_quotation")
	private long idQuotation;
	
	@ManyToOne
	@JoinColumn(name="id_quotation", insertable=false, updatable=false)
	private Quotation quotation;
	
	@Column(name="id_vendor")
	private long idVendor;
	
	@ManyToOne
	@JoinColumn(name="id_vendor", insertable=false, updatable=false)
	private Vendor vendor;
	
	private String poNumber;
	
	private Date poDate;
	
	private double poAmount;
	
	private double tax;
	
	private double totalAmount;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idPurchaseOrder")
	private List<PurchaseOrderItem> items;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceId")
	private List<ApprovalDetails> approvalList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdQuotation() {
		return idQuotation;
	}

	public void setIdQuotation(long idQuotation) {
		this.idQuotation = idQuotation;
	}

	public Quotation getQuotation() {
		return quotation;
	}

	public void setQuotation(Quotation quotation) {
		this.quotation = quotation;
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

	public double getPoAmount() {
		return poAmount;
	}

	public void setPoAmount(double quoteAmount) {
		this.poAmount = quoteAmount;
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

	public List<PurchaseOrderItem> getItems() {
		return items;
	}

	public void setItems(List<PurchaseOrderItem> items) {
		this.items = items;
	}

	public List<ApprovalDetails> getApprovalList() {
		return approvalList;
	}

	public void setApprovalList(List<ApprovalDetails> approvalList) {
		this.approvalList = approvalList;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public Date getPoDate() {
		return poDate;
	}

	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}
	
}
