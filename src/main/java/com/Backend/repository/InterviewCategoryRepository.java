package com.Backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Backend.entity.InterviewCategory;

public interface InterviewCategoryRepository
        extends JpaRepository<InterviewCategory, Long> {

    Optional<InterviewCategory> findByNameIgnoreCase(String name);

    List<InterviewCategory> findAllByOrderByNameAsc();
}