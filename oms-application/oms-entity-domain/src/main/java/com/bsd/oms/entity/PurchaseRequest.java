package com.bsd.oms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="purchase_requests")
public class PurchaseRequest {

	@Id
	@GeneratedValue
	private long id;
	
	private String requestNo;
	
	private Date requestDate;
	
	private String department;
	
	@ManyToOne
	@JoinColumn(name="department", insertable=false, updatable=false)
	private Department departmentDetails;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idPurchseRequest")
	private List<PurchaseItem> items;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceId")
	private List<ApprovalDetails> approvalList;
	
	private String requestedBy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
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

	public List<ApprovalDetails> getApprovalList() {
		return approvalList;
	}

	public void setApprovalList(List<ApprovalDetails> approvalList) {
		this.approvalList = approvalList;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public Department getDepartmentDetails() {
		return departmentDetails;
	}

	public void setDepartmentDetails(Department departmentDetails) {
		this.departmentDetails = departmentDetails;
	}
	
}
