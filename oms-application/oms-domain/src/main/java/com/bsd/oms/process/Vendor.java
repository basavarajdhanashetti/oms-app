package com.bsd.oms.process;

public class Vendor implements java.io.Serializable {

	static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private String emailId;
	private String contactNo;
	private Address address;
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
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Vendor(){
		
	}
	
	public Vendor(long id, String name, String emailId, String contactNo,
			Address address) {
		super();
		this.id = id;
		this.name = name;
		this.emailId = emailId;
		this.contactNo = contactNo;
		this.address = address;
	}

	
	
}
