package com.Backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Backend.entity.MandatoryTraining;
import com.Backend.entity.User;
import com.Backend.entity.UserMandatoryTrainingStatus;

public interface UserMandatoryTrainingStatusRepository
        extends JpaRepository<UserMandatoryTrainingStatus, Long> {

    Optional<UserMandatoryTrainingStatus>
    findByUserAndMandatoryTraining(
            User user,
            MandatoryTraining mandatoryTraining);
    
    List<UserMandatoryTrainingStatus>
    findByUser(User user);
}