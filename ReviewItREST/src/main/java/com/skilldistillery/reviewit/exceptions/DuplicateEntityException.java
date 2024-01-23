package com.skilldistillery.reviewit.exceptions;

import java.util.Map;

public class DuplicateEntityException extends Exception {

	private static final long serialVersionUID = 4646059574770889809L;

	private Map<String, String> errors;

	public DuplicateEntityException(Map<String, String> errors) {
		this.errors = errors;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

}
