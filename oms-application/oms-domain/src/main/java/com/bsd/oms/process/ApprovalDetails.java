package com.bsd.oms.process;


public class ApprovalDetails implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	private String userId;
	
	private String approvedDate;
	
	public ApprovalDetails(){}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}

	public ApprovalDetails(String userId, String approvedDate) {
		super();
		this.userId = userId;
		this.approvedDate = approvedDate;
	}
	
}
