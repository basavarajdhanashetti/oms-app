package com.bsd.oms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="quotation_items")
public class QuotationItem {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="id_quotation")
	private long idQuotation;
	
	@Column(name="id_product")
	private long idProduct;
	
	@ManyToOne
	@JoinColumn(name="id_product", insertable=false, updatable=false)
	private Product product;
	
	private int quantity;
	
	private String description;
	
	private double unitPrice;
	
	private double discount;
	
	private double salePrice;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdQuotation() {
		return idQuotation;
	}

	public void setIdQuotation(long idQuotation) {
		this.idQuotation = idQuotation;
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

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	
}
