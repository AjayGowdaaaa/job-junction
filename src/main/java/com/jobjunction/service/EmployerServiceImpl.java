package com.jobjunction.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jobjunction.dto.EmployerDTO;
import com.jobjunction.entity.Candidate;
import com.jobjunction.entity.Employer;
import com.jobjunction.entity.Skills;
import com.jobjunction.exception.CustomException;
import com.jobjunction.repository.CandidateRepo;
import com.jobjunction.repository.EmployerRepo;
import com.jobjunction.repository.SkillRepo;
import com.jobjunction.response.ResponseModel;

@Service
public class EmployerServiceImpl implements EmployerService {

	@Autowired
	EmployerRepo employerRepo;
	@Autowired
	SkillRepo skillRepo;

	@Autowired
	CandidateRepo candidateRepo;

	private void validateEmail(String email) throws CustomException {
		Employer employer = employerRepo.findByEmail(email);
		if (employer != null)
			throw new CustomException(HttpStatus.CONFLICT, "409-B", "Email ID already exist");
	}

	@Override
	public ResponseModel regestierAsEmployer(EmployerDTO employerDTO) throws CustomException {
		validateEmail(employerDTO.getEmail());

		Employer employer = new Employer();
		BeanUtils.copyProperties(employerDTO, employer);
		employerRepo.saveAndFlush(employer);

		return new ResponseModel(HttpStatus.CREATED, "Employer details added sucesfully", employerDTO);
	}

	@Override
	public ResponseModel updateEmployer(EmployerDTO employerDTO, Long id) throws CustomException {
		Employer employer = employerRepo.findById(id)
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "404-B", "Employer details not found"));

		BeanUtils.copyProperties(employerDTO, employer);
		employerRepo.saveAndFlush(employer);
		return new ResponseModel(HttpStatus.ACCEPTED, "Employer details updated sucesfully", employerDTO);
	}

	@Override
	public ResponseModel getAllEmployers() throws CustomException {
		List<Employer> fetchedEmployers = employerRepo.findAll();
		if (fetchedEmployers == null || fetchedEmployers.isEmpty())
			throw new CustomException(HttpStatus.NOT_FOUND, "404-B", "Employer details not found");

		List<EmployerDTO> employers = new ArrayList<>();

		for (Employer employer : fetchedEmployers) {
			EmployerDTO employerDTO = new EmployerDTO();
			BeanUtils.copyProperties(employer, employerDTO);
			employers.add(employerDTO);
		}
		return new ResponseModel(HttpStatus.OK, "Employer details fetched sucesfully", employers);
	}

	@Override
	public ResponseModel getAllEmployersByLocation(String location) throws CustomException {
		List<Employer> fetchedEmployers = employerRepo.findByLocation(location);
		if (fetchedEmployers == null || fetchedEmployers.isEmpty())
			throw new CustomException(HttpStatus.NOT_FOUND, "404-B", "Employer details not found");

		List<EmployerDTO> employers = new ArrayList<>();

		for (Employer employer : fetchedEmployers) {
			EmployerDTO employerDTO = new EmployerDTO();
			BeanUtils.copyProperties(employer, employerDTO);
			employers.add(employerDTO);
		}
		return new ResponseModel(HttpStatus.OK, "Employer details fetched sucesfully", employers);
	}

	@Override
	public ResponseModel getAllEmployersByIndustry(String industry) throws CustomException {
		List<Employer> fetchedEmployers = employerRepo.findByIndustry(industry);
		if (fetchedEmployers == null || fetchedEmployers.isEmpty())
			throw new CustomException(HttpStatus.NOT_FOUND, "404-B", "Employer details not found");

		List<EmployerDTO> employers = new ArrayList<>();

		for (Employer employer : fetchedEmployers) {
			EmployerDTO employerDTO = new EmployerDTO();
			BeanUtils.copyProperties(employer, employerDTO);
			employers.add(employerDTO);
		}
		return new ResponseModel(HttpStatus.OK, "Employer details fetched sucesfully", employers);
	}

	@Override
	public ResponseModel getAllProductBasedCompany() throws CustomException {
		List<Employer> fetchedEmployers = employerRepo.findByProductBasedTrue();
		if (fetchedEmployers == null || fetchedEmployers.isEmpty())
			throw new CustomException(HttpStatus.NOT_FOUND, "404-B", "Employer details not found");

		List<EmployerDTO> employers = new ArrayList<>();

		for (Employer employer : fetchedEmployers) {
			EmployerDTO employerDTO = new EmployerDTO();
			BeanUtils.copyProperties(employer, employerDTO);
			employers.add(employerDTO);
		}
		return new ResponseModel(HttpStatus.OK, "Employer details fetched sucesfully", employers);

	}

	@Override
	public ResponseModel getAllServiceBasedCompany() throws CustomException {
		List<Employer> fetchedEmployers = employerRepo.findByServiceBasedTrue();
		if (fetchedEmployers == null || fetchedEmployers.isEmpty())
			throw new CustomException(HttpStatus.NOT_FOUND, "404-B", "Employer details not found");

		List<EmployerDTO> employers = new ArrayList<>();

		for (Employer employer : fetchedEmployers) {
			EmployerDTO employerDTO = new EmployerDTO();
			BeanUtils.copyProperties(employer, employerDTO);
			employers.add(employerDTO);
		}
		return new ResponseModel(HttpStatus.OK, "Employer details fetched sucesfully", employers);

	}

	private boolean verifySkills(List<Skills> candidateSKills, List<Skills> jobSkills) {
		for (Skills skill : jobSkills) {
			if (candidateSKills.contains(skill)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ResponseModel searchCandidatesBySkills(List<String> skills) {
		Set<Candidate> candidates = new HashSet<>();

		for (String skill : skills) {
			Skills skillObj = skillRepo.findBySkill(skill).orElseThrow();
			if (skillObj != null) {
				List<Candidate> candidate = candidateRepo.findBySkills(skillObj);
				candidates.addAll(candidate);
			}
		}

		return new ResponseModel(HttpStatus.OK, "Candidates details fetched sucesfully", candidates);
	}
}
