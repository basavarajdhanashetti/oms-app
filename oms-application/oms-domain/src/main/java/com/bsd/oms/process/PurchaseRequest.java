package com.bsd.oms.process;

import java.util.List;

public class PurchaseRequest implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	private long id; 
	private String requestNo;
	private String requestDate;
	private String department;
	private List<PurchaseItem> items;
	private ApprovalDetails approver;
	private String requestedBy;
	
	public PurchaseRequest(){
		
	}
	
	public PurchaseRequest(long id, String requestNo, String requestDate,
			String department, List<PurchaseItem> items, String requestedBy) {
		super();
		this.id = id;
		this.requestNo = requestNo;
		this.requestDate = requestDate;
		this.department = department;
		this.items = items;
		this.requestedBy = requestedBy;
	}
	
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public List<PurchaseItem> getItems() {
		return items;
	}
	public void setItems(List<PurchaseItem> items) {
		this.items = items;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public ApprovalDetails getApprover() {
		return approver;
	}

	public void setApprover(ApprovalDetails approver) {
		this.approver = approver;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
