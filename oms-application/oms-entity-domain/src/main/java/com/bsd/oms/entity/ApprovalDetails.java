package com.bsd.oms.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="approval_details")
public class ApprovalDetails {

	@Id
	@GeneratedValue
	private long id;
	
	private String userId;
	
	private Date createdDate;
	
	@Enumerated(EnumType.STRING)
	private ApprovalStatusType status;
	
	@Enumerated(EnumType.STRING)
	private MaterialType material;
	
	private String comments;
	
	private long referenceId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public ApprovalStatusType getStatus() {
		return status;
	}

	public void setStatus(ApprovalStatusType status) {
		this.status = status;
	}

	public MaterialType getMaterial() {
		return material;
	}

	public void setMaterial(MaterialType material) {
		this.material = material;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(long referenceId) {
		this.referenceId = referenceId;
	}
	
	public enum MaterialType{
		PurchaseRequest, Quotation, PurchaseOrder
		
	}
	
	public enum ApprovalStatusType{
		Submited, Approved, Rejected, Aborted, Completed
	}
}
