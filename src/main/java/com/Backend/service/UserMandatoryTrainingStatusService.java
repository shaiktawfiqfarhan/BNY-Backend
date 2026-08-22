package com.Backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.TrainingStatusRequest;
import com.Backend.dto.UserMandatoryTrainingStatusResponse;
import com.Backend.entity.MandatoryTraining;
import com.Backend.entity.User;
import com.Backend.entity.UserMandatoryTrainingStatus;
import com.Backend.exception.MandatoryTrainingNotFoundException;
import com.Backend.repository.MandatoryTrainingRepository;
import com.Backend.repository.UserMandatoryTrainingStatusRepository;
import com.Backend.repository.UserRepository;

@Service
public class UserMandatoryTrainingStatusService {

    private final UserRepository userRepository;

    private final MandatoryTrainingRepository
            mandatoryTrainingRepository;

    private final UserMandatoryTrainingStatusRepository
            userMandatoryTrainingStatusRepository;

    public UserMandatoryTrainingStatusService(
            UserRepository userRepository,
            MandatoryTrainingRepository mandatoryTrainingRepository,
            UserMandatoryTrainingStatusRepository userMandatoryTrainingStatusRepository) {

        this.userRepository = userRepository;
        this.mandatoryTrainingRepository =
                mandatoryTrainingRepository;
        this.userMandatoryTrainingStatusRepository =
                userMandatoryTrainingStatusRepository;
    }

    public ApiResponse<UserMandatoryTrainingStatusResponse>
    updateTrainingStatus(
            Long trainingId,
            TrainingStatusRequest request) {

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

        MandatoryTraining training =
                mandatoryTrainingRepository
                        .findById(trainingId)
                        .orElseThrow(() ->
                                new MandatoryTrainingNotFoundException(
                                        "Mandatory training not found"));

        UserMandatoryTrainingStatus status =
                userMandatoryTrainingStatusRepository
                        .findByUserAndMandatoryTraining(
                                user,
                                training)
                        .orElseGet(() -> {

                            UserMandatoryTrainingStatus
                                    newStatus =
                                    new UserMandatoryTrainingStatus();

                            newStatus.setUser(user);
                            newStatus.setMandatoryTraining(
                                    training);

                            return newStatus;
                        });

        status.setStatus(
                request.getStatus());

        UserMandatoryTrainingStatus saved =
                userMandatoryTrainingStatusRepository
                        .save(status);

        return new ApiResponse<>(
                true,
                "Training status updated successfully",
                mapToResponse(saved));
    }

    public ApiResponse<
            List<UserMandatoryTrainingStatusResponse>>
    getMyTrainingStatuses() {

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

        List<UserMandatoryTrainingStatusResponse>
                response =
                userMandatoryTrainingStatusRepository
                        .findByUser(user)
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new ApiResponse<>(
                true,
                "Training statuses fetched successfully",
                response);
    }

    private UserMandatoryTrainingStatusResponse
    mapToResponse(
            UserMandatoryTrainingStatus status) {

        UserMandatoryTrainingStatusResponse
                response =
                new UserMandatoryTrainingStatusResponse();

        response.setTrainingId(
                status.getMandatoryTraining()
                        .getId());

        response.setTrainingTitle(
                status.getMandatoryTraining()
                        .getTitle());

        response.setStatus(
                status.getStatus());

        return response;
    }
}