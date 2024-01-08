package com.skilldistillery.reviewit.controllers;

import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.exceptions.TokenInvalidException;
import com.skilldistillery.reviewit.util.ThrowingRunnable;
import com.skilldistillery.reviewit.util.ThrowingSupplier;

import jakarta.servlet.http.HttpServletResponse;

public abstract class BaseController {
	protected <T> T tryFailableAction(ThrowingSupplier<T> action, HttpServletResponse res) {

		T item = null;
		try {
			item = action.get();
		} catch (Exception e) {
			if (e instanceof EntityDoesNotExistException) {
				res.setStatus(404);
			} else if (e instanceof TokenInvalidException) {
				res.setStatus(401);
			} else {
				res.setStatus(400);
			}
		}

		return item;

	}
	
	protected void tryFailableAction(ThrowingRunnable action, HttpServletResponse res) {
		tryFailableAction(()->{
			action.run();
			return true;
		}, res);
	}
}
