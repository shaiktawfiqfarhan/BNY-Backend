package com.Backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.Backend.entity.PocAssignment;
import com.Backend.entity.PointOfContact;
import com.Backend.entity.User;

public interface PocAssignmentRepository extends JpaRepository<PocAssignment, Long> {

    List<PocAssignment> findByUser(User user);
    Optional<PocAssignment> findByUserAndPointOfContact(User user,PointOfContact pointOfContact);
    List<PocAssignment> findByPointOfContact(PointOfContact pointOfContact);
    @Modifying
    @Transactional
    @Query("""
           delete from PocAssignment p
           where p.user.id = :userId
           """)
    void deleteAllByUserId(
            @Param("userId") Long userId);
    @Modifying
    @Transactional
    @Query("""
           delete from PocAssignment p
           where p.user.id = :userId
           and p.pointOfContact.id = :pocId
           """)
    void deleteByUserIdAndPocId(
            @Param("userId") Long userId,
            @Param("pocId") Long pocId);
}