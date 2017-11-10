package com.bsd.oms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="purchase_request_items")
public class PurchaseItem {

	@Id
	@GeneratedValue
	private long id;

	@Column(name="id_purchase_request")
	private long idPurchseRequest;
	
	@Column(name="id_product")
	private long idProduct;
	
	@ManyToOne
	@JoinColumn(name="id_product", insertable=false, updatable=false)
	private Product product;
	
	private int quantity;
	
	private String description;

	
	public PurchaseItem(){
		
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getIdPurchseRequest() {
		return idPurchseRequest;
	}


	public void setIdPurchseRequest(long idPurchseRequest) {
		this.idPurchseRequest = idPurchseRequest;
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


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
}
