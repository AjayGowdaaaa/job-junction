package com.jobjunction.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobjunction.entity.Employer;
import com.jobjunction.entity.Job;

public interface JobRepo extends JpaRepository<Job, Long> {

	List<Job> findByTittle(String searchByRole);

	List<Job> findByEmployer(Employer employer);

	List<Job> findByLocation(String location);

}
