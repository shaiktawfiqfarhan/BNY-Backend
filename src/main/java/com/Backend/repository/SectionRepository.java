package com.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Backend.entity.Section;

public interface SectionRepository extends JpaRepository<Section, Long>{
	boolean existsByNameIgnoreCase(String name);
}
