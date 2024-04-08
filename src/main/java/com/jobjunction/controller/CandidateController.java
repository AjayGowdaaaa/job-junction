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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobjunction.dto.CandidateDTO;
import com.jobjunction.exception.CustomException;
import com.jobjunction.response.ResponseModel;
import com.jobjunction.service.CandidateService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@RestController
@Tag(name = "Candidate Management")
public class CandidateController {

	@Autowired
	private CandidateService candidateService;

	@PostMapping("/candidate")
	public ResponseEntity<?> createCandidate(@RequestBody CandidateDTO candidateDTO) {
		try {
			ResponseModel responseModel = candidateService.createCandidate(candidateDTO);

			return new ResponseEntity<Map<String, Object>>(responseModel.getResponse(responseModel),
					responseModel.getHttpStatus());
		} catch (CustomException e) {
			return new ResponseEntity<Map<String, String>>(e.getErrors(e), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong, Due to : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/candidate")
	public ResponseEntity<?> updateCandidate(@RequestBody CandidateDTO candidateDTO, Long id) {
		try {
			ResponseModel responseModel = candidateService.updateCandidate(candidateDTO, id);

			return new ResponseEntity<Map<String, Object>>(responseModel.getResponse(responseModel),
					responseModel.getHttpStatus());
		} catch (CustomException e) {
			return new ResponseEntity<Map<String, String>>(e.getErrors(e), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong, Due to : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/candidate")
	public ResponseEntity<?> getCandidateById(Long id) {
		try {
			ResponseModel responseModel = candidateService.getCandidate(id);

			return new ResponseEntity<Map<String, Object>>(responseModel.getResponse(responseModel),
					responseModel.getHttpStatus());
		} catch (CustomException e) {
			return new ResponseEntity<Map<String, String>>(e.getErrors(e), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong, Due to : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/candidate")
	public ResponseEntity<?> deleteCandidateById(Long id, Boolean confirm) {
		try {
			ResponseModel responseModel = candidateService.deactivateCandidate(id, confirm);

			return new ResponseEntity<Map<String, Object>>(responseModel.getResponse(responseModel),
					responseModel.getHttpStatus());
		} catch (CustomException e) {
			return new ResponseEntity<Map<String, String>>(e.getErrors(e), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong, Due to : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/search-candidate")
	public ResponseEntity<?> searchCandidate(
			@Parameter(description = "Search by First name, Last name, Mobile, Email", required = true) @RequestParam String searchElement) {
		try {
			ResponseModel responseModel = candidateService.searchCandidate(searchElement);

			return new ResponseEntity<Map<String, Object>>(responseModel.getResponse(responseModel),
					responseModel.getHttpStatus());
		} catch (CustomException e) {
			return new ResponseEntity<Map<String, String>>(e.getErrors(e), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong, Due to : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/candidates")
	public ResponseEntity<?> getAllCandidates() {
		try {
			ResponseModel responseModel = candidateService.getAllCandidates();

			return new ResponseEntity<Map<String, Object>>(responseModel.getResponse(responseModel),
					responseModel.getHttpStatus());
		} catch (CustomException e) {
			return new ResponseEntity<Map<String, String>>(e.getErrors(e), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong, Due to : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//
	// @Value("${project.images}")
	// private String path;

	// @PostMapping("/upload")
	// public ResponseEntity<?> uploadDocument(Long empId, String type,
	// MultipartFile file) {
	// ResponseModel fileName = candidateService.uploadDocument(path, file, empId,
	// type);
	// return new ResponseEntity<>(new FileResponse(fileName, "Image Uploaded"),
	// HttpStatus.CREATED);
	// }

	// @GetMapping(value = "/document", produces = MediaType.IMAGE_JPEG_VALUE)
	// public void getProfilePicture(Long empId, String type, HttpServletResponse
	// response) throws IOException {
	// InputStream resource = candidateService.getDocument(path, empId, type);
	// response.setContentType(MediaType.ALL_VALUE);
	// StreamUtils.copy(resource, response.getOutputStream());
	// }

}
