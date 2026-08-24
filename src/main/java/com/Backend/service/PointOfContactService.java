package com.Backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import com.Backend.entity.User;
import com.Backend.entity.PocAssignment;
import com.Backend.repository.UserRepository;
import com.Backend.repository.PocAssignmentRepository;
import com.Backend.dto.ApiResponse;
import com.Backend.dto.PointOfContactRequest;
import com.Backend.dto.PointOfContactResponse;
import com.Backend.entity.PointOfContact;
import com.Backend.exception.PointOfContactAlreadyExistsException;
import com.Backend.exception.PointOfContactNotFoundException;
import com.Backend.repository.PointOfContactRepository;

@Service
public class PointOfContactService {

	private final PointOfContactRepository pointOfContactRepository;
	private final UserRepository userRepository;
	private final PocAssignmentRepository pocAssignmentRepository;

    public PointOfContactService(
            PointOfContactRepository pointOfContactRepository,
            UserRepository userRepository,
            PocAssignmentRepository pocAssignmentRepository) {

        this.pointOfContactRepository = pointOfContactRepository;
        this.userRepository = userRepository;
        this.pocAssignmentRepository = pocAssignmentRepository;
    }

    public ApiResponse<PointOfContactResponse>
    createPointOfContact(
            PointOfContactRequest request) {

        if (pointOfContactRepository
                .existsByEmailIgnoreCase(
                        request.getEmail())) {

            throw new PointOfContactAlreadyExistsException(
                    "Point of contact already exists");
        }

        PointOfContact pointOfContact =
                new PointOfContact();

        pointOfContact.setName(
                request.getName());
        pointOfContact.setDesignation(
                request.getDesignation());
        pointOfContact.setEmail(
                request.getEmail());
        pointOfContact.setPhoneNumber(
                request.getPhoneNumber());
        pointOfContact.setActive(
                request.getActive());

        PointOfContact saved =
                pointOfContactRepository.save(
                        pointOfContact);

        return new ApiResponse<>(
                true,
                "Point of contact created successfully",
                mapToResponse(saved));
    }

    public ApiResponse<List<PointOfContactResponse>>
    getAllPointOfContacts() {

        String username =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        User user =
                userRepository.findByUsername(
                        username)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"));

        List<PointOfContactResponse> contacts =
                pocAssignmentRepository
                        .findByUser(user)
                        .stream()
                        .map(PocAssignment::getPointOfContact)
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new ApiResponse<>(
                true,
                "Point of contacts fetched successfully",
                contacts);
    }

    public ApiResponse<PointOfContactResponse>
    getPointOfContactById(Long id) {

        PointOfContact contact =
                pointOfContactRepository.findById(id)
                        .orElseThrow(() ->
                                new PointOfContactNotFoundException(
                                        "Point of contact not found"));

        return new ApiResponse<>(
                true,
                "Point of contact fetched successfully",
                mapToResponse(contact));
    }
    
    public ApiResponse<List<PointOfContactResponse>>
    getAllPointOfContactsForAdmin() {

        List<PointOfContactResponse> contacts =
                pointOfContactRepository
                        .findAll()
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new ApiResponse<>(
                true,
                "Point of contacts fetched successfully",
                contacts);
    }

    public ApiResponse<PointOfContactResponse>
    updatePointOfContact(
            Long id,
            PointOfContactRequest request) {

        PointOfContact contact =
                pointOfContactRepository.findById(id)
                        .orElseThrow(() ->
                                new PointOfContactNotFoundException(
                                        "Point of contact not found"));

        contact.setName(
                request.getName());
        contact.setDesignation(
                request.getDesignation());
        contact.setEmail(
                request.getEmail());
        contact.setPhoneNumber(
                request.getPhoneNumber());
        contact.setActive(
                request.getActive());

        PointOfContact updated =
                pointOfContactRepository.save(
                        contact);

        return new ApiResponse<>(
                true,
                "Point of contact updated successfully",
                mapToResponse(updated));
    }

    public ApiResponse<Object>
    deletePointOfContact(Long id) {

        PointOfContact contact =
                pointOfContactRepository.findById(id)
                        .orElseThrow(() ->
                                new PointOfContactNotFoundException(
                                        "Point of contact not found"));

        pointOfContactRepository.delete(
                contact);

        return new ApiResponse<>(
                true,
                "Point of contact deleted successfully",
                null);
    }

    private PointOfContactResponse
    mapToResponse(
            PointOfContact contact) {

        PointOfContactResponse response =
                new PointOfContactResponse();

        response.setId(contact.getId());
        response.setName(contact.getName());
        response.setDesignation(
                contact.getDesignation());
        response.setEmail(
                contact.getEmail());
        response.setPhoneNumber(
                contact.getPhoneNumber());
        response.setActive(
                contact.getActive());

        return response;
    }
}