package com.Backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Backend.entity.PocAssignment;
import com.Backend.entity.PointOfContact;
import com.Backend.entity.User;

public interface PocAssignmentRepository extends JpaRepository<PocAssignment, Long> {

    List<PocAssignment> findByUser(User user);
    Optional<PocAssignment> findByUserAndPointOfContact(User user,PointOfContact pointOfContact);
}