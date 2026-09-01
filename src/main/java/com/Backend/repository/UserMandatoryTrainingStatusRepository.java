package com.Backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Backend.entity.MandatoryTraining;
import com.Backend.entity.User;
import com.Backend.entity.UserMandatoryTrainingStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserMandatoryTrainingStatusRepository
        extends JpaRepository<UserMandatoryTrainingStatus, Long> {

    Optional<UserMandatoryTrainingStatus>
    findByUserAndMandatoryTraining(
            User user,
            MandatoryTraining mandatoryTraining);
    
    List<UserMandatoryTrainingStatus>
    findByUser(User user);
    
    @Modifying
    @Transactional
    @Query("""
           delete from UserMandatoryTrainingStatus s
           where s.user.id = :userId
           """)
    void deleteAllByUserId(
            @Param("userId") Long userId);
}