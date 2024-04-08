package com.jobjunction.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpStatus httpStatus;
	private String errorCode;
	private String errorMessage;

	public Map<String, String> getErrors(CustomException customException) {
		Map<String, String> errors = new LinkedHashMap<>();
		errors.put("error_code", customException.getErrorCode());
		errors.put("error_message", customException.getErrorMessage());
		return errors;
	}

	public Map<String, String> getMessage(CustomException e) {
		// TODO Auto-generated method stub
		return null;
	}
}
