package com.jobjunction.response;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.jobjunction.exception.CustomException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel {

	private HttpStatus httpStatus;
	private String message;
	private Object data;
	
	
	public Map<String, Object> getResponse(ResponseModel responseModel) {
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("message", responseModel.getMessage());
		if (responseModel.getData() != null) {
			response.put("data", responseModel.getData());
		}
		
		return response;
	}


	public ResponseModel(HttpStatus httpStatus, String message) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
