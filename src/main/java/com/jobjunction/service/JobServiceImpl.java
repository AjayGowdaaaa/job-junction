package com.jobjunction.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jobjunction.dto.JobDTO;
import com.jobjunction.entity.Application;
import com.jobjunction.entity.Candidate;
import com.jobjunction.entity.Employer;
import com.jobjunction.entity.Job;
import com.jobjunction.entity.Skills;
import com.jobjunction.exception.CustomException;
import com.jobjunction.repository.ApplicationRepo;
import com.jobjunction.repository.CandidateRepo;
import com.jobjunction.repository.EmployerRepo;
import com.jobjunction.repository.JobRepo;
import com.jobjunction.repository.SkillRepo;
import com.jobjunction.response.ResponseModel;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	JobRepo jobRepo;

	@Autowired
	EmployerRepo employerRepo;

	@Autowired
	private SkillRepo skillRepo;

	@Autowired
	CandidateRepo candidateRepo;

	@Autowired
	ApplicationRepo applicationRepo;

	private List<Skills> addSkillsIfNotExist(JobDTO jobDTO) {

		List<String> skillNames = jobDTO.getSkills();
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

	private boolean verifySkills(List<Skills> candidateSKills, List<Skills> jobSkills) {
		for (Skills skill : jobSkills) {
			if (candidateSKills.contains(skill)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ResponseModel postJob(JobDTO jobDTO, Long employerId) throws CustomException {
		Employer employer = employerRepo.findById(employerId)
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "404-B", "Employer details not found"));

		List<Skills> skills = addSkillsIfNotExist(jobDTO);

		Job job = new Job();
		BeanUtils.copyProperties(jobDTO, job);
		job.setSkills(skills);
		job.setEmployer(employer);
		skillRepo.saveAll(skills);
		jobRepo.saveAndFlush(job);

		return new ResponseModel(HttpStatus.CREATED, "Job posted sucesfully", jobDTO);
	}

	@Override
	public ResponseModel updateJob(Long jobId, Long employerId, JobDTO jobDTO) throws CustomException {
		Employer employer = employerRepo.findById(employerId)
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "404-B", "Employer details not found"));

		Job job = jobRepo.findById(jobId)
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "404-B", "Job details not found"));
		if (job.getEmployer() != employer) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "400-A", "Job update access denied");
		}

		List<Skills> skills = addSkillsIfNotExist(jobDTO);
		BeanUtils.copyProperties(jobDTO, job);
		job.setSkills(skills);
		job.setEmployer(employer);
		skillRepo.saveAll(skills);
		jobRepo.saveAndFlush(job);

		return new ResponseModel(HttpStatus.OK, "Job updated sucesfully", jobDTO);
	}

	@Override
	public ResponseModel deleteJob(Long jobId, Long employerId) throws CustomException {
		Employer employer = employerRepo.findById(employerId)
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "404-B", "Employer details not found"));

		Job job = jobRepo.findById(jobId)
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "404-B", "Job details not found"));
		if (job.getEmployer() != employer) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "400-A", "Job update access denied");
		}
		jobRepo.delete(job);
		return new ResponseModel(HttpStatus.OK, "Job deleted sucesfully");
	}

	@Override
	public ResponseModel applyForJob(Long jobId, Long canidateId) throws CustomException {
		Candidate candidate = candidateRepo.findById(canidateId)
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "404-A", "Candidate details not found"));
		Job job = jobRepo.findById(jobId)
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "404-B", "Job details not found"));

		List<Skills> candidateSKills = candidate.getSkills();
		List<Skills> jobSkills = job.getSkills();

		if (verifySkills(candidateSKills, jobSkills)) {
			Application application = new Application();
			application.setCandidate(candidate);
			application.setJob(job);
			applicationRepo.save(application);

		} else {
			throw new CustomException(HttpStatus.BAD_REQUEST, "404-D",
					"Candidate skills doesn't match with required skill");
		}

		return new ResponseModel(HttpStatus.OK, "Sucesfully appied for Job");
	}

	private List<JobDTO> getListOfJobDtoByJobs(List<Job> jobs) {
		List<JobDTO> listOfJobs = new LinkedList<>();

		for (Job job : jobs) {
			JobDTO jobDTO = new JobDTO();
			BeanUtils.copyProperties(job, jobDTO);
			List<Skills> skills = job.getSkills();
			List<String> skillsList = new LinkedList<>();

			for (Skills listOfSkills : skills)
				skillsList.add(listOfSkills.getSkill());

			jobDTO.setSkills(skillsList);

			listOfJobs.add(jobDTO);
		}
		return listOfJobs;
	}

	@Override
	public ResponseModel searchJobByRole(String searchByRole) throws CustomException {
		List<Job> jobs = jobRepo.findByTittle(searchByRole);
		if (jobs.isEmpty())
			throw new CustomException(HttpStatus.NOT_FOUND, "404-B", "Job details not found");
		
		List<JobDTO> listOfJobs = getListOfJobDtoByJobs(jobs);
		return new ResponseModel(HttpStatus.OK, "Job details fetched sucesfully", listOfJobs);
	}

	@Override
	public ResponseModel searchJobByCompany(String company) throws CustomException {
		Employer employer = employerRepo.findByName(company);
		List<Job> jobs = jobRepo.findByEmployer(employer);
		if (jobs.isEmpty())
			throw new CustomException(HttpStatus.NOT_FOUND, "404-B", "Job details not found");

		List<JobDTO> listOfJobs = getListOfJobDtoByJobs(jobs);
		return new ResponseModel(HttpStatus.OK, "Job details fetched sucesfully", listOfJobs);
	}

	@Override
	public ResponseModel searchJobByLocation(String location) throws CustomException {
		List<Job> jobs = jobRepo.findByLocation(location);
		if (jobs.isEmpty())
			throw new CustomException(HttpStatus.NOT_FOUND, "404-B", "Job details not found");

		List<JobDTO> listOfJobs = getListOfJobDtoByJobs(jobs);
		return new ResponseModel(HttpStatus.OK, "Job details fetched sucesfully", listOfJobs);
	}

}
