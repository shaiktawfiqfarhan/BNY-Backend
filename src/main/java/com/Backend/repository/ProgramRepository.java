package com.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Backend.entity.Program;

public interface ProgramRepository extends JpaRepository<Program, Long> {

	boolean existsByTitleIgnoreCase(String title);
}
