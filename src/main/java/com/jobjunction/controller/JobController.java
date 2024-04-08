package com.jobjunction.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jobjunction.dto.JobDTO;
import com.jobjunction.exception.CustomException;
import com.jobjunction.response.ResponseModel;
import com.jobjunction.service.JobService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@RestController
@Tag(name = "Job Management")
public class JobController {

	@Autowired
	JobService jobService;

	@PostMapping("/job")
	public ResponseEntity<?> postJob(@RequestBody JobDTO jobDTO, Long employerId) {
		try {
			ResponseModel responseModel = jobService.postJob(jobDTO, employerId);

			return new ResponseEntity<Map<String, Object>>(responseModel.getResponse(responseModel),
					responseModel.getHttpStatus());
		} catch (CustomException e) {
			return new ResponseEntity<Map<String, String>>(e.getErrors(e), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong, Due to : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/job")
	public ResponseEntity<?> updateJob(@RequestBody JobDTO jobDTO, Long jobId, Long employerId) {
		try {
			ResponseModel responseModel = jobService.updateJob(jobId, employerId, jobDTO);

			return new ResponseEntity<Map<String, Object>>(responseModel.getResponse(responseModel),
					responseModel.getHttpStatus());
		} catch (CustomException e) {
			return new ResponseEntity<Map<String, String>>(e.getErrors(e), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong, Due to : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/job")
	public ResponseEntity<?> deleteJob(Long jobId, Long employerId) {
		try {
			ResponseModel responseModel = jobService.deleteJob(jobId, employerId);

			return new ResponseEntity<Map<String, Object>>(responseModel.getResponse(responseModel),
					responseModel.getHttpStatus());
		} catch (CustomException e) {
			return new ResponseEntity<Map<String, String>>(e.getErrors(e), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong, Due to : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/apply")
	public ResponseEntity<?> applyForJob(Long jobId, Long canidateId) {
		try {
			ResponseModel responseModel = jobService.applyForJob(jobId, canidateId);

			return new ResponseEntity<Map<String, Object>>(responseModel.getResponse(responseModel),
					responseModel.getHttpStatus());
		} catch (CustomException e) {
			return new ResponseEntity<Map<String, String>>(e.getErrors(e), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong, Due to : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/search-role")
	public ResponseEntity<?> searchJobByRole(String role) {
		try {
			ResponseModel responseModel = jobService.searchJobByRole(role);

			return new ResponseEntity<Map<String, Object>>(responseModel.getResponse(responseModel),
					responseModel.getHttpStatus());
		} catch (CustomException e) {
			return new ResponseEntity<Map<String, String>>(e.getErrors(e), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong, Due to : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/search-company")
	public ResponseEntity<?> searchJobByCompany(String company) {
		try {
			ResponseModel responseModel = jobService.searchJobByCompany(company);

			return new ResponseEntity<Map<String, Object>>(responseModel.getResponse(responseModel),
					responseModel.getHttpStatus());
		} catch (CustomException e) {
			return new ResponseEntity<Map<String, String>>(e.getErrors(e), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong, Due to : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/search-location")
	public ResponseEntity<?> searchJobByLocation(String location) {
		try {
			ResponseModel responseModel = jobService.searchJobByLocation(location);

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
