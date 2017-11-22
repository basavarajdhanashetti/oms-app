package com.bsd.oms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bsd.oms.entity.StoreInwardRegister.Status;

@Entity
@Table(name = "asset_requests")
public class AssetRequest {

	@Id
	@GeneratedValue
	private long id;

	private String userId;

	@Column(name = "id_product")
	private long idProduct;

	@ManyToOne
	@JoinColumn(name = "id_product", insertable = false, updatable = false)
	private Product product;

	private String requestNo;
	
	private long requestedCount;

	private Date requestDate;

	@Enumerated(EnumType.STRING)
	private Status status;

	private String approvedBy;

	private String comments;

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

	public long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(long idProduct) {
		this.idProduct = idProduct;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getRequestedCount() {
		return requestedCount;
	}

	public void setRequestedCount(long requestedCount) {
		this.requestedCount = requestedCount;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

}
