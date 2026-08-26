package com.Backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.RecordedSessionRequest;
import com.Backend.dto.RecordedSessionResponse;
import com.Backend.entity.RecordedSession;
import com.Backend.entity.Training;
import com.Backend.exception.TrainingNotFoundException;
import com.Backend.repository.RecordedSessionRepository;
import com.Backend.repository.TrainingRepository;

@Service
public class RecordedSessionService {

    private final RecordedSessionRepository
            recordedSessionRepository;

    private final TrainingRepository
            trainingRepository;

    public RecordedSessionService(
            RecordedSessionRepository recordedSessionRepository,
            TrainingRepository trainingRepository) {

        this.recordedSessionRepository =
                recordedSessionRepository;

        this.trainingRepository =
                trainingRepository;
    }

    public ApiResponse<RecordedSessionResponse>
    createRecordedSession(
            RecordedSessionRequest request) {
    	
    	System.out.println(request.getTitle());
    	System.out.println(request.getRecordingUrl());
    	System.out.println(request.getTrainingId());

        Training training =
                trainingRepository
                        .findById(
                                request.getTrainingId())
                        .orElseThrow(() ->
                                new TrainingNotFoundException(
                                        "Training not found"));

        System.out.println(
                "Training = " +
                training.getId());
        
        RecordedSession session =
                new RecordedSession();

        session.setTitle(
                request.getTitle());

        session.setRecordingUrl(
                request.getRecordingUrl());

        session.setTraining(
                training);

        RecordedSession saved =
                recordedSessionRepository
                        .save(session);

        return new ApiResponse<>(
                true,
                "Recorded session created successfully",
                mapToResponse(saved));
    }

    public ApiResponse<List<RecordedSessionResponse>>
    getSessionsByTraining(
            Long trainingId) {

        List<RecordedSessionResponse> sessions =
                recordedSessionRepository
                        .findByTrainingId(
                                trainingId)
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new ApiResponse<>(
                true,
                "Recorded sessions fetched successfully",
                sessions);
    }

    public ApiResponse<Object>
    deleteRecordedSession(
            Long id) {

        RecordedSession session =
                recordedSessionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recorded session not found"));

        recordedSessionRepository.delete(
                session);

        return new ApiResponse<>(
                true,
                "Recorded session deleted successfully",
                null);
    }

    private RecordedSessionResponse
    mapToResponse(
            RecordedSession session) {

        RecordedSessionResponse response =
                new RecordedSessionResponse();

        response.setId(
                session.getId());

        response.setTitle(
                session.getTitle());

        response.setRecordingUrl(
                session.getRecordingUrl());

        return response;
    }
}