package com.skilldistillery.reviewit.dtos.validation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface ValidPassword {
	String message() default "Password must be at least eight characters long, and contain at least one letter and one number.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
