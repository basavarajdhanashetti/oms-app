package com.bsd.oms.domain;

public class Error {

	public Error(String errorCode, String errormessage) {
		super();
		this.errorCode = errorCode;
		this.errormessage = errormessage;
	}

	private String errorCode;
	private String errormessage;

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrormessage() {
		return errormessage;
	}

}
