package com.bsd.oms.process;

import java.util.List;

public class PRQuotations  implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	private List<Quotation> quotations;
	
	private ApprovalDetails approver;
	
	private long[] vendorIds;
	
	public PRQuotations(){
		
	}
	
	public List<Quotation> getQuotations() {
		return quotations;
	}

	public void setQuotations(List<Quotation> quotations) {
		this.quotations = quotations;
	}

	public long[] getVendorIds() {
		return vendorIds;
	}

	public void setVendorIds(long[] vendorIds) {
		this.vendorIds = vendorIds;
	}

	public ApprovalDetails getApprover() {
		return approver;
	}

	public void setApprover(ApprovalDetails approver) {
		this.approver = approver;
	}
	
}
