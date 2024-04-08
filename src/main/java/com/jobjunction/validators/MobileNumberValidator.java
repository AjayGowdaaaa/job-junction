package com.jobjunction.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MobileNumberValidator implements ConstraintValidator<ValidMobileNumber, String> {

	@Override
	public void initialize(ValidMobileNumber constraintAnnotation) {
	}

	@Override
	public boolean isValid(String mobileNumber, ConstraintValidatorContext constraintValidatorContext) {
		return mobileNumber != null && mobileNumber.matches("\\d{10}");
	}
}
