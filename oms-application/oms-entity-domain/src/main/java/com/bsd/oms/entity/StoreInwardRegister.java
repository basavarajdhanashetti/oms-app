package com.bsd.oms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="store_inward_register")
public class StoreInwardRegister {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="id_purchase_order")
	private long idPurchaseOrder;
	
	@ManyToOne
	@JoinColumn(name="id_purchase_order", insertable=false, updatable=false)
	private PurchaseOrder purchaseOrder;
	
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	private String verifiedBy;
	
	private String approvedBy;
	
	private Date createdDate;
	
	private String comments;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="idInwardRegister")
	private List<StoreInwardItem> items;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdPurchaseOrder() {
		return idPurchaseOrder;
	}

	public void setIdPurchaseOrder(long idPurchaseOrder) {
		this.idPurchaseOrder = idPurchaseOrder;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public List<StoreInwardItem> getItems() {
		return items;
	}

	public void setItems(List<StoreInwardItem> items) {
		this.items = items;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public enum Status {
		SAVED, PENDING_APPROVAL, APPROVED, REJECTED
	}
}
