package com.Backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.TrainingRequest;
import com.Backend.dto.TrainingResponse;
import com.Backend.entity.Training;
import com.Backend.entity.TrainingType;
import com.Backend.exception.TrainingNotFoundException;
import com.Backend.repository.RecordedSessionRepository;
import com.Backend.repository.TrainingRepository;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;
    
    private final RecordedSessionRepository recordedSessionRepository;

    public TrainingService(TrainingRepository trainingRepository, RecordedSessionRepository recordedSessionRepository) {

        this.trainingRepository = trainingRepository;
		this.recordedSessionRepository = recordedSessionRepository;
    }

    public ApiResponse<TrainingResponse>
    createTraining(
            TrainingRequest request) {

        Training training = new Training();

        training.setTitle(request.getTitle());

        training.setDescription(request.getDescription());

        training.setSharePointUrl(request.getSharePointUrl());

        training.setTrainingType(TrainingType.valueOf(request.getTrainingType()));

        training.setActive(request.getActive());

        Training saved = trainingRepository.save(training);

        return new ApiResponse<>(
                true,
                "Training created successfully",
                mapToResponse(saved));
    }

    public ApiResponse<List<TrainingResponse>>
    getAllTrainings() {

        List<TrainingResponse> trainings =
                trainingRepository.findAll()
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new ApiResponse<>(
                true,
                "Trainings fetched successfully",
                trainings);
    }

    public ApiResponse<TrainingResponse>
    getTrainingById(Long id) {

        Training training =
                trainingRepository.findById(id)
                        .orElseThrow(() ->
                                new TrainingNotFoundException(
                                        "Training not found"));

        return new ApiResponse<>(
                true,
                "Training fetched successfully",
                mapToResponse(training));
    }

    public ApiResponse<List<TrainingResponse>>
    getTrainingsByType(
            String type) {

        List<TrainingResponse> trainings =
                trainingRepository
                        .findByTrainingType(
                                TrainingType.valueOf(type))
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new ApiResponse<>(
                true,
                "Trainings fetched successfully",
                trainings);
    }

    public ApiResponse<TrainingResponse>
    updateTraining(
            Long id,
            TrainingRequest request) {

        Training training =
                trainingRepository.findById(id)
                        .orElseThrow(() ->
                                new TrainingNotFoundException(
                                        "Training not found"));

        training.setTitle(
                request.getTitle());

        training.setDescription(
                request.getDescription());

        training.setSharePointUrl(
                request.getSharePointUrl());

        training.setTrainingType(
                TrainingType.valueOf(
                        request.getTrainingType()));

        training.setActive(
                request.getActive());

        Training updated =
                trainingRepository.save(
                        training);

        return new ApiResponse<>(
                true,
                "Training updated successfully",
                mapToResponse(updated));
    }

    @Transactional
    public ApiResponse<Object>
    deleteTraining(
            Long id) {

        Training training =
                trainingRepository.findById(id)
                        .orElseThrow(() ->
                                new TrainingNotFoundException(
                                        "Training not found"));
        
        recordedSessionRepository.deleteAllByTrainingId(id);

        trainingRepository.delete(
                training);

        return new ApiResponse<>(
                true,
                "Training deleted successfully",
                null);
    }

    private TrainingResponse
    mapToResponse(
            Training training) {

        TrainingResponse response =
                new TrainingResponse();

        response.setId(
                training.getId());

        response.setTitle(
                training.getTitle());

        response.setDescription(
                training.getDescription());

        response.setSharePointUrl(
                training.getSharePointUrl());

        response.setTrainingType(
                training.getTrainingType()
                        .name());

        response.setActive(
                training.getActive());

        return response;
    }
}