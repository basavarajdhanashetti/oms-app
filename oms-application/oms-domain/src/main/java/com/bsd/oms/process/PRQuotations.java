package com.bsd.oms.process;

import java.util.List;

public class PRQuotations {

	private PurchaseRequest purchaseRequest;
	
	private List<Quotation> quotations;

	public PurchaseRequest getPurchaseRequest() {
		return purchaseRequest;
	}

	public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
		this.purchaseRequest = purchaseRequest;
	}

	public List<Quotation> getQuotations() {
		return quotations;
	}

	public void setQuotations(List<Quotation> quotations) {
		this.quotations = quotations;
	}
	
}
