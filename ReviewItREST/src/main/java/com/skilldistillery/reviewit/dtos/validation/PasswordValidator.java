package com.skilldistillery.reviewit.dtos.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
	private static final String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d\\W]{8,}$";
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean valid = false;
		boolean preCheck = value != null && !value.isBlank() && 8 <= value.length() && value.length() < 120; 
		if (preCheck) {
			Pattern pattern = Pattern.compile(passwordRegex);
			Matcher matcher = pattern.matcher(value);
			valid = matcher.find();
		}
		return valid;
	}

}
