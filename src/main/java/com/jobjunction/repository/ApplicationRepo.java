package com.jobjunction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobjunction.entity.Application;

public interface ApplicationRepo extends JpaRepository<Application, Long> {

}
