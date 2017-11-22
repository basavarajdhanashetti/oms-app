package com.bsd.oms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="store_inward_items")
public class StoreInwardItem {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="id_inward_register")
	private long idInwardRegister;
	
	@Column(name="id_product")
	private long idProduct;
	
	@ManyToOne
	@JoinColumn(name="id_product", insertable=false, updatable=false)
	private Product product;
	
	private int orderedQty;
	
	private int receivedQty;
	
	private String comments;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdInwardRegister() {
		return idInwardRegister;
	}

	public void setIdInwardRegister(long idInwardRegister) {
		this.idInwardRegister = idInwardRegister;
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

	public int getOrderedQty() {
		return orderedQty;
	}

	public void setOrderedQty(int orderedQty) {
		this.orderedQty = orderedQty;
	}

	public int getReceivedQty() {
		return receivedQty;
	}

	public void setReceivedQty(int receivedQty) {
		this.receivedQty = receivedQty;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
