package com.bsd.oms.process;

public class Quotation implements java.io.Serializable {

	static final long serialVersionUID = 1L;
	
	private long id;
	
	private long vendorId;
	
	private long category;
	
	private long subCategory;
	
	private long productId;

	private String desciprtion;
	
	private int quantity;

	private int unitPrice;
	
	private int discount;

	private int salePrice;


	public long getCategory() {
		return category;
	}

	public void setCategory(long category) {
		this.category = category;
	}

	public long getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(long subCategory) {
		this.subCategory = subCategory;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getDesciprtion() {
		return desciprtion;
	}

	public void setDesciprtion(String desciprtion) {
		this.desciprtion = desciprtion;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	
	public Quotation(){
		
	}

	public Quotation(long id, long vendorId, String requestNo, long category,
			long subCategory, long productId, String desciprtion, int quantity,
			int unitPrice, int discount, int salePrice) {
		super();
		this.id = id;
		this.vendorId = vendorId;
		this.category = category;
		this.subCategory = subCategory;
		this.productId = productId;
		this.desciprtion = desciprtion;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.discount = discount;
		this.salePrice = salePrice;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVendorId() {
		return vendorId;
	}

	public void setVendorId(long vendorId) {
		this.vendorId = vendorId;
	}

}
