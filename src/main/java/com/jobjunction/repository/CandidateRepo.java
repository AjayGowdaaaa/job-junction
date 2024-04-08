package com.jobjunction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobjunction.entity.Candidate;
import com.jobjunction.entity.Skills;

public interface CandidateRepo extends JpaRepository<Candidate, Long> {
	
	List<Candidate> findByFirstName(String firstName);

	List<Candidate> findByGender(String gender);

	Candidate findByEmail(String email);

	Candidate findByMobile(String mobile);
	
	List<Candidate> findBySkills(Skills skill);
	
	List<Candidate> findByLastNameOrFirstNameOrEmailOrMobile(String firtstName, String lastName, String email, String mobile);

}
