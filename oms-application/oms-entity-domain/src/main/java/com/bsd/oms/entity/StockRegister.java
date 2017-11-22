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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.bsd.oms.entity.StoreInwardRegister.Status;

@Entity
@Table(name = "stock_register")
@NamedQueries({ @NamedQuery(name = "StockRegister.findUnallocatedAssetsForProduct", query = "SELECT sr FROM StockRegister sr where sr.idProduct=:idProduct and sr.status = 'APPROVED' and sr.assignedTo is null "),
 @NamedQuery(name="StockRegister.findAllocatedAssets", query="SELECT sr FROM StockRegister sr where sr.status = 'APPROVED' and sr.assignedTo=:assignedTo")	
})
public class StockRegister {

	@Id
	@GeneratedValue
	private long id;

	@Column(name = "id_inward_item")
	private long idInwardItem;

	@ManyToOne
	@JoinColumn(name = "id_inward_item", insertable = false, updatable = false)
	private StoreInwardItem inwardItem;

	@Column(name = "id_product")
	private long idProduct;

	@ManyToOne
	@JoinColumn(name = "id_product", insertable = false, updatable = false)
	private Product product;

	private String assetNo;

	private String serialNo;

	private String model;

	private Date entryDate;

	private String enteredBy;

	private String assignedTo;

	@Enumerated(EnumType.STRING)
	private Status status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdInwardItem() {
		return idInwardItem;
	}

	public void setIdInwardItem(long idInwardItem) {
		this.idInwardItem = idInwardItem;
	}

	public StoreInwardItem getInwardItem() {
		return inwardItem;
	}

	public void setInwardItem(StoreInwardItem inwardItem) {
		this.inwardItem = inwardItem;
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

	public String getAssetNo() {
		return assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
