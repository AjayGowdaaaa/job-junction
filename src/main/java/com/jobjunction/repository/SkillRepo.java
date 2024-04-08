package com.jobjunction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jobjunction.entity.Skills;

public interface SkillRepo extends JpaRepository<Skills, Long> {
	
	Optional<Skills> findBySkill(String skillName);

	@Query(value = "SELECT s.skill FROM skills s JOIN candidate_skills cs ON s.id = cs.skill_id WHERE cs.candidate_id = ?", nativeQuery = true)
	List<String> getAllSkillsByCandidateId(Long id);


}
