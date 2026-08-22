package com.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Backend.entity.OnboardingFile;

public interface OnboardingFileRepository
        extends JpaRepository<OnboardingFile, Long> {

    boolean existsByTitleIgnoreCase(String title);
}