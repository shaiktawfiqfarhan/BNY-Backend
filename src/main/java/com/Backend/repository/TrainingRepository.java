package com.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Backend.entity.Training;
import com.Backend.entity.TrainingType;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findByTrainingType(TrainingType trainingType);
}