package com.jobjunction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobjunction.entity.Employer;

@Repository
public interface EmployerRepo extends JpaRepository<Employer, Long> {

	Employer findByEmail(String email);

	List<Employer> findByLocation(String location);

	List<Employer> findByIndustry(String industry);

	List<Employer> findByProductBasedTrue();

	List<Employer> findByServiceBasedTrue();
	
	Employer findByName(String name);
	
	

}
