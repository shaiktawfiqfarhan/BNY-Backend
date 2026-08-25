package com.Backend.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.Backend.dto.PocAssignmentRequest;
import com.Backend.entity.PocAssignment;
import com.Backend.entity.PointOfContact;
import com.Backend.entity.User;
import com.Backend.exception.PointOfContactNotFoundException;
import com.Backend.exception.UserNotFoundException;
import com.Backend.repository.PocAssignmentRepository;
import com.Backend.repository.PointOfContactRepository;
import com.Backend.repository.UserRepository;

@Service
public class PocAssignmentService {

    private final PocAssignmentRepository pocAssignmentRepository;
    private final UserRepository userRepository;
    private final PointOfContactRepository pointOfContactRepository;

    public PocAssignmentService(
            PocAssignmentRepository pocAssignmentRepository,
            UserRepository userRepository,
            PointOfContactRepository pointOfContactRepository) {

        this.pocAssignmentRepository = pocAssignmentRepository;
        this.userRepository = userRepository;
        this.pointOfContactRepository = pointOfContactRepository;
    }

    public void assignPocToUser(
            PocAssignmentRequest request) {

        User user =
                userRepository.findById(
                        request.getUserId())
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found"));

        PointOfContact poc =
                pointOfContactRepository.findById(
                        request.getPointOfContactId())
                        .orElseThrow(() ->
                                new PointOfContactNotFoundException(
                                        "POC not found"));
        
        if (pocAssignmentRepository
                .findByUserAndPointOfContact(user, poc)
                .isPresent()) {
            return;
        }

        PocAssignment assignment =
                new PocAssignment();

        assignment.setUser(user);
        assignment.setPointOfContact(poc);

        pocAssignmentRepository.save(
                assignment);
    }
    
    public List<String> getAssignedEmployees(Long pocId) {

        PointOfContact poc =
                pointOfContactRepository
                        .findById(pocId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "POC not found"));

        return pocAssignmentRepository
                .findByPointOfContact(poc)
                .stream()
                .map(a ->
                    a.getUser()
                     .getFullName())
                .toList();
    }
    
    public List<PointOfContact> getMyPocs() {

        String username =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        User user =
                userRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"));

        return pocAssignmentRepository
                .findByUser(user)
                .stream()
                .map(
                    assignment ->
                        assignment.getPointOfContact())
                .toList();
    }
}