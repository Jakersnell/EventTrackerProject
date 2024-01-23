package com.skilldistillery.reviewit.exceptions;

import java.util.HashMap;

public class BadRequestException extends Exception {

	private static final long serialVersionUID = 4431819839350331526L;
	
	private HashMap<String, String> requestParamErrors;
	
	public BadRequestException(HashMap<String, String> requestParamErrors) {
		this.requestParamErrors = requestParamErrors;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public HashMap<String, String> getRequestParamErrors() {
		return requestParamErrors;
	}

}
