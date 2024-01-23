package com.skilldistillery.reviewit.exceptions;

import java.util.HashMap;

public class DuplicateEntityException extends Exception {

	private static final long serialVersionUID = 4646059574770889809L;

	private HashMap<String, String> errors;

	public DuplicateEntityException(HashMap<String, String> errors) {
		this.errors = errors;
	}

	public HashMap<String, String> getErrors() {
		return errors;
	}

}
