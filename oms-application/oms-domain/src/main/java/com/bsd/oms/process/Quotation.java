package com.bsd.oms.process;

import java.util.List;

public class Quotation implements java.io.Serializable {

	static final long serialVersionUID = 1L;
	
	private long id;
	
	private Vendor vendor;
	
	private String desciprtion;
	
	private String requestNo;
	
	private List<PurchaseItem> items;
	
	private double quoteAmount;
	
	private double tax;
	
	private double totalAmount;
		
	
	public Quotation(){
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public String getDesciprtion() {
		return desciprtion;
	}

	public void setDesciprtion(String desciprtion) {
		this.desciprtion = desciprtion;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public List<PurchaseItem> getItems() {
		return items;
	}

	public void setItems(List<PurchaseItem> items) {
		this.items = items;
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

	public double getQuoteAmount() {
		return quoteAmount;
	}

	public void setQuoteAmount(double quoteAmount) {
		this.quoteAmount = quoteAmount;
	}


}
