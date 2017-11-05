package com.bsd.oms.process;


public class Address  implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	private long id;
	
	private String line1;
	
	private String line2;
	
	private String line3;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private int pinCode;
	
	private String region;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getLine3() {
		return line3;
	}

	public void setLine3(String line3) {
		this.line3 = line3;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getPinCode() {
		return pinCode;
	}

	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Address(){
		
	}
	
	public Address(long id, String line1, String line2, String line3,
			String city, String state, String country, int pinCode,
			String region) {
		super();
		this.id = id;
		this.line1 = line1;
		this.line2 = line2;
		this.line3 = line3;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pinCode = pinCode;
		this.region = region;
	}
	
	
}
