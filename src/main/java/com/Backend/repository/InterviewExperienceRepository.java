package com.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Backend.entity.InterviewExperience;

public interface InterviewExperienceRepository
        extends JpaRepository<InterviewExperience, Long> {

    List<InterviewExperience>
    findByCategoryId(Long categoryId);
}