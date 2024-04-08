package com.jobjunction.service;

import com.jobjunction.dto.CandidateDTO;
import com.jobjunction.exception.CustomException;
import com.jobjunction.response.ResponseModel;

public interface CandidateService {

	public ResponseModel createCandidate(CandidateDTO dto) throws CustomException;

	public ResponseModel updateCandidate(CandidateDTO dto, Long id) throws CustomException;

	public ResponseModel getCandidate(Long id) throws CustomException;

	public ResponseModel deactivateCandidate(Long id, Boolean confirm) throws CustomException;

	public ResponseModel searchCandidate(String searchElement) throws CustomException;

	public ResponseModel getAllCandidates() throws CustomException;

	// public ResponseModel uploadDocument(String path, MultipartFile file, Long
	// empId, String type);
	//
	// public InputStream getDocument(String path, Long empId, String type);

}
