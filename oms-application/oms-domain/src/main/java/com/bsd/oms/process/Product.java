package com.bsd.oms.process;


public class Product  implements java.io.Serializable {

	static final long serialVersionUID = 1L;

    private Long id;
	
	private String name;

	private long productSubCategoryId;

	private ProductSubCategory productSubCategory;
	
	private String container;
	
	private double unitPrice;
	
	private double baseMargin;

	public Product(){
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public ProductSubCategory getProductSubCategory() {
		return productSubCategory;
	}

	public void setProductSubCategory(ProductSubCategory productSubCategory) {
		this.productSubCategory = productSubCategory;
	}

}

