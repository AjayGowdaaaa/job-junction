package com.jobjunction.service;

import com.jobjunction.dto.JobDTO;
import com.jobjunction.exception.CustomException;
import com.jobjunction.response.ResponseModel;

public interface JobService {

	public ResponseModel postJob(JobDTO jobDTO, Long employerId) throws CustomException;

	public ResponseModel updateJob(Long jobId, Long employerId, JobDTO jobDTO) throws CustomException;

	public ResponseModel deleteJob(Long jobId, Long employerId) throws CustomException;

	public ResponseModel applyForJob(Long jobId, Long canidateId) throws CustomException;

	public ResponseModel searchJobByRole(String role)throws CustomException;

	public ResponseModel searchJobByCompany(String company)throws CustomException;

	public ResponseModel searchJobByLocation(String location)throws CustomException;

}
