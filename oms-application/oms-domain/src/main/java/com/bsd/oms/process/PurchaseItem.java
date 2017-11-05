package com.bsd.oms.process;



public class PurchaseItem implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	private long id;
	
	private long category;
	
	private long subCategory;
	
	private long productId;
	
	private Product product;
	
	private int quantity;
	
	private String description;

	
	public PurchaseItem(){
		
	}
	
	public PurchaseItem(long id, long category, long subCategory, long productId,
			int quantity, String desciprtion) {
		super();
		this.id = id;
		this.category = category;
		this.subCategory = subCategory;
		this.productId = productId;
		this.quantity = quantity;
		this.setDescription(desciprtion);
	}

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
