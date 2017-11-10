package com.bsd.oms.process;


public class ApprovalDetails implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	private String userId;
	
	private String createdDate;
	
	public ApprovalDetails(){}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ApprovalDetails(String userId, String createdDate) {
		super();
		this.userId = userId;
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "ApprovalDetails [userId=" + userId + ", createdDate=" + createdDate + "]";
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
}
