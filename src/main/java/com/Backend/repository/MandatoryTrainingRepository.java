package com.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Backend.entity.MandatoryTraining;

public interface MandatoryTrainingRepository
        extends JpaRepository<MandatoryTraining, Long> {

    boolean existsByTitleIgnoreCase(String title);
}