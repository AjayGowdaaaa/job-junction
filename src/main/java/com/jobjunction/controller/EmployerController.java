package com.jobjunction.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jobjunction.dto.EmployerDTO;
import com.jobjunction.exception.CustomException;
import com.jobjunction.response.ResponseModel;
import com.jobjunction.service.EmployerService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@RestController
@Tag(name = "Employer Management")
public class EmployerController {

	@Autowired
	private EmployerService employerService;

	@PostMapping("/employer")
	public ResponseEntity<?> regestierAsEmployer(@RequestBody EmployerDTO employer) {
		try {
			ResponseModel responseModel = employerService.regestierAsEmployer(employer);

			return new ResponseEntity<Map<String, Object>>(responseModel.getResponse(responseModel),
					responseModel.getHttpStatus());
		} catch (CustomException e) {
			return new ResponseEntity<Map<String, String>>(e.getErrors(e), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong, Due to : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/employer")
	public ResponseEntity<?> updateEmployer(@RequestBody EmployerDTO employer, Long id) {
		try {
			ResponseModel responseModel = employerService.updateEmployer(employer, id);

			return new ResponseEntity<Map<String, Object>>(responseModel.getResponse(responseModel),
					responseModel.getHttpStatus());
		} catch (CustomException e) {
			return new ResponseEntity<Map<String, String>>(e.getErrors(e), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong, Due to : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/employers")
	public ResponseEntity<?> getAllEmployers() {
		try {
			ResponseModel responseModel = employerService.getAllEmployers();

			return new ResponseEntity<Map<String, Object>>(responseModel.getResponse(responseModel),
					responseModel.getHttpStatus());
		} catch (CustomException e) {
			return new ResponseEntity<Map<String, String>>(e.getErrors(e), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong, Due to : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/employers/location")
	public ResponseEntity<?> getAllEmployersByLocation(String location) {
		try {
			ResponseModel responseModel = employerService.getAllEmployersByLocation(location);

			return new ResponseEntity<Map<String, Object>>(responseModel.getResponse(responseModel),
					responseModel.getHttpStatus());
		} catch (CustomException e) {
			return new ResponseEntity<Map<String, String>>(e.getErrors(e), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong, Due to : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/employer/industry")
	public ResponseEntity<?> getAllEmployersByIndustry(String industry) {
		try {
			ResponseModel responseModel = employerService.getAllEmployersByIndustry(industry);

			return new ResponseEntity<Map<String, Object>>(responseModel.getResponse(responseModel),
					responseModel.getHttpStatus());
		} catch (CustomException e) {
			return new ResponseEntity<Map<String, String>>(e.getErrors(e), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong, Due to : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/employer/product-based")
	public ResponseEntity<?> getAllProductBasedCompany() {
		try {
			ResponseModel responseModel = employerService.getAllProductBasedCompany();

			return new ResponseEntity<Map<String, Object>>(responseModel.getResponse(responseModel),
					responseModel.getHttpStatus());
		} catch (CustomException e) {
			return new ResponseEntity<Map<String, String>>(e.getErrors(e), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong, Due to : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/employer/service-based")
	public ResponseEntity<?> getAllServiceBasedCompany() {
		try {
			ResponseModel responseModel = employerService.getAllServiceBasedCompany();

			return new ResponseEntity<Map<String, Object>>(responseModel.getResponse(responseModel),
					responseModel.getHttpStatus());
		} catch (CustomException e) {
			return new ResponseEntity<Map<String, String>>(e.getErrors(e), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong, Due to : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
