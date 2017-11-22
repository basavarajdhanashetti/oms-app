package com.bsd.oms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@Column(name = "id")
	@GeneratedValue
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "id_prod_sub_category")
	private long productSubCategoryId;

	@ManyToOne
	@JoinColumn(name = "id_prod_sub_category", insertable = false, updatable = false)
	private ProductSubCategory productSubCategory;

	@Column(name = "container")
	private String container;

	@Column(name = "unit_price")
	private double unitPrice;

	@Column(name = "base_margin")
	private double baseMargin;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getProductSubCategoryId() {
		return productSubCategoryId;
	}

	public void setProductSubCategoryId(long productSubCategoryId) {
		this.productSubCategoryId = productSubCategoryId;
	}

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getBaseMargin() {
		return baseMargin;
	}

	public void setBaseMargin(double baseMargin) {
		this.baseMargin = baseMargin;
	}

	public ProductSubCategory getProductSubCategory() {
		return productSubCategory;
	}

	public void setProductSubCategory(ProductSubCategory productSubCategory) {
		this.productSubCategory = productSubCategory;
	}

}
