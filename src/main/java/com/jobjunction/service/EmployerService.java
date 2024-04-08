package com.jobjunction.service;

import java.util.List;

import com.jobjunction.dto.EmployerDTO;
import com.jobjunction.exception.CustomException;
import com.jobjunction.response.ResponseModel;

public interface EmployerService {

	ResponseModel regestierAsEmployer(EmployerDTO employer) throws CustomException;

	ResponseModel updateEmployer(EmployerDTO employer, Long id) throws CustomException;

	ResponseModel getAllEmployers() throws CustomException;

	ResponseModel getAllEmployersByLocation(String location) throws CustomException;

	ResponseModel getAllEmployersByIndustry(String industry) throws CustomException;

	ResponseModel getAllProductBasedCompany() throws CustomException;

	ResponseModel getAllServiceBasedCompany() throws CustomException;

	ResponseModel searchCandidatesBySkills(List<String> skills);

}
