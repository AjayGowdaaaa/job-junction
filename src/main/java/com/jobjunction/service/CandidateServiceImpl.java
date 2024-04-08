package com.jobjunction.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jobjunction.dto.CandidateDTO;
import com.jobjunction.entity.Candidate;
import com.jobjunction.entity.Skills;
import com.jobjunction.exception.CustomException;
import com.jobjunction.repository.CandidateRepo;
import com.jobjunction.repository.SkillRepo;
import com.jobjunction.response.ResponseModel;

@Service
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	private CandidateRepo candidateRepo;

	@Autowired
	private SkillRepo skillRepo;

	private void validateMobile(String mobile) throws CustomException {
		Candidate candidate = candidateRepo.findByMobile(mobile);
		if (candidate != null) {
			throw new CustomException(HttpStatus.CONFLICT, "409-A", "Mobile number already exist");
		}
	}

	private void validateEmail(String email) throws CustomException {
		Candidate candidate = candidateRepo.findByEmail(email);
		if (candidate != null) {
			throw new CustomException(HttpStatus.CONFLICT, "409-B", "Email ID already exist");
		}

	}

	private List<Skills> addSkillsIfNotExist(CandidateDTO candidateDTO) {

		List<String> skillNames = candidateDTO.getSkills();
		List<Skills> skills = new ArrayList<>();
		for (String skillName : skillNames) {
			Optional<Skills> existingSkillOptional = skillRepo.findBySkill(skillName);

			Skills skill = existingSkillOptional.orElseGet(() -> {
				Skills newSkill = new Skills();
				newSkill.setSkill(skillName);
				return newSkill;
			});

			skills.add(skill);
		}
		return skills;
	}

	@Override
	public ResponseModel createCandidate(CandidateDTO dto) throws CustomException {

		validateMobile(dto.getMobile());
		validateEmail(dto.getEmail());

		List<Skills> skills = addSkillsIfNotExist(dto);

		Candidate candidate = new Candidate();
		BeanUtils.copyProperties(dto, candidate);
		candidate.setSkills(skills);

		skillRepo.saveAll(skills);
		candidateRepo.saveAndFlush(candidate);

		return new ResponseModel(HttpStatus.CREATED, "Candidate created sucesfully", dto);
	}

	@Override
	public ResponseModel updateCandidate(CandidateDTO candidateDTO, Long id) throws CustomException {

		Candidate candidate = candidateRepo.findById(id)
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "404-A", "Candidate details not found"));

		if (candidateDTO.getEmail() != candidate.getEmail())
			validateEmail(candidateDTO.getEmail());
		if (candidateDTO.getMobile() != candidate.getMobile())
			validateMobile(candidateDTO.getMobile());

		List<Skills> skills = addSkillsIfNotExist(candidateDTO);

		BeanUtils.copyProperties(candidateDTO, candidate);
		candidate.setSkills(skills);

		skillRepo.saveAll(skills);
		candidate = candidateRepo.saveAndFlush(candidate);

		return new ResponseModel(HttpStatus.ACCEPTED, "Candidate details updated sucesfully", candidateDTO);
	}

	@Override
	public ResponseModel getCandidate(Long id) throws CustomException {
		Candidate candidate = candidateRepo.findById(id)
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "404-A", "Candidate details not found"));

		CandidateDTO candidateDTO = new CandidateDTO();
		BeanUtils.copyProperties(candidate, candidateDTO);

		List<String> skills = skillRepo.getAllSkillsByCandidateId(candidate.getId());
		candidateDTO.setSkills(skills);
		return new ResponseModel(HttpStatus.OK, "Candidate details fetched sucesfully", candidateDTO);
	}

	@Override
	public ResponseModel deactivateCandidate(Long id, Boolean confirm) throws CustomException {
		Candidate candidate = candidateRepo.findById(id)
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "404-A", "Candidate details not found"));
		if (confirm) {
			candidateRepo.delete(candidate);
		} else {
			throw new CustomException(HttpStatus.CONFLICT, "409-C", "Deletion Confirmation Required");
		}

		return new ResponseModel(HttpStatus.OK, "Candidate details deleted sucesfully");
	}

	public ResponseModel searchCandidate(String searchElement) throws CustomException {
		List<Candidate> fetchedCandidates = candidateRepo.findByLastNameOrFirstNameOrEmailOrMobile(searchElement,
				searchElement, searchElement, searchElement);

		if (fetchedCandidates == null || fetchedCandidates.isEmpty())
			throw new CustomException(HttpStatus.NOT_FOUND, "404-A", "Candidate details not found");

		List<CandidateDTO> candidates = new ArrayList<>();

		for (Candidate fetchedCandidate : fetchedCandidates) {
			CandidateDTO candidateDTO = new CandidateDTO();
			BeanUtils.copyProperties(fetchedCandidate, candidateDTO);

			List<String> skills = skillRepo.getAllSkillsByCandidateId(fetchedCandidate.getId());
			candidateDTO.setSkills(skills);
			candidates.add(candidateDTO);
		}

		return new ResponseModel(HttpStatus.OK, "Candidate details fetched sucesfully", candidates);
	}

	public ResponseModel getAllCandidates() throws CustomException {
		List<Candidate> fetchedCandidates = candidateRepo.findAll();

		if (fetchedCandidates == null || fetchedCandidates.isEmpty())
			throw new CustomException(HttpStatus.NOT_FOUND, "404-A", "Candidate details not found");

		List<CandidateDTO> candidates = new ArrayList<>();

		for (Candidate fetchedCandidate : fetchedCandidates) {
			CandidateDTO candidateDTO = new CandidateDTO();
			BeanUtils.copyProperties(fetchedCandidate, candidateDTO);

			List<String> skills = skillRepo.getAllSkillsByCandidateId(fetchedCandidate.getId());
			candidateDTO.setSkills(skills);
			candidates.add(candidateDTO);
		}

		return new ResponseModel(HttpStatus.OK, "Candidate details fetched sucesfully", candidates);
	}

	// int count = 0;
	//
	// @Override
	// public ResponseModel uploadDocument(String path, MultipartFile file, Long
	// empId, String type) {
	//
	// Candidate candidate = candidateRepo.findById(empId).get();
	// ;
	//
	// // File name
	// String fileName = candidate.getFirstName() + count++;
	// // Full path
	// String filePath = path + fileName;
	// // Setting path
	// if (type.equals("profilePicture")) {
	// candidate.setProfilePicturePath(filePath);
	// // Setting photo name
	// candidate.setProfilePictureName(filePath);
	// try {
	// candidate.setProfilePicture(file.getBytes());
	// } catch (IOException e1) {
	// e1.printStackTrace();
	// }
	// } else if (type.equals("resume")) {
	// candidate.setResumePath(filePath);
	// // Setting photo name
	// candidate.setResumeName(fileName);
	// try {
	// candidate.setResume(file.getBytes());
	// } catch (IOException e1) {
	// e1.printStackTrace();
	// }
	// }
	//
	// // Create folder
	// File f = new File(path);
	// if (!f.exists()) {
	// f.mkdir();
	// }
	// // file copy
	// try {
	// Files.copy(file.getInputStream(), Paths.get(filePath));
	// } catch (IOException e) {
	//
	// }
	// candidateRepo.save(candidate);
	// return new ResponseModel();
	// }
	//
	// @Override
	// public InputStream getDocument(String path, Long empId, String type) {
	// Candidate candidate = candidateRepo.findById(empId).get();
	//
	// String fullPath = null;
	//
	// if (type.equals("profilePicture")) {
	// fullPath = path + File.separator + candidate.getProfilePictureName();
	// } else if (type.equals("resume")) {
	// fullPath = path + File.separator + candidate.getResumeName();
	// }
	// System.out.println(fullPath);
	//
	// InputStream inputStream = null;
	// try {
	// // DataBase logic to return inputstream
	// inputStream = new FileInputStream(fullPath);
	//
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// }
	// return inputStream;
	// }
}
