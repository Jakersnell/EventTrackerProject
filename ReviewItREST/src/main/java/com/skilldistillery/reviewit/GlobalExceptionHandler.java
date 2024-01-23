package com.skilldistillery.reviewit;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.skilldistillery.reviewit.exceptions.BadRequestException;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.exceptions.UserDoesNotExistException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ EntityDoesNotExistException.class, UserDoesNotExistException.class })
	public ResponseEntity<Object> handle404Exception(RuntimeException ex) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler({ BadRequestException.class })
	public ResponseEntity<Object> handle400Exception(BadRequestException ex) {
		return ResponseEntity.badRequest().body(ex.getRequestParamErrors());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return ResponseEntity.badRequest().body(errors);
	}

}
