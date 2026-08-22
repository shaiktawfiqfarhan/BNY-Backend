package com.Backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.MandatoryTrainingRequest;
import com.Backend.dto.MandatoryTrainingResponse;
import com.Backend.entity.MandatoryTraining;
import com.Backend.exception.MandatoryTrainingAlreadyExistsException;
import com.Backend.exception.MandatoryTrainingNotFoundException;
import com.Backend.repository.MandatoryTrainingRepository;

@Service
public class MandatoryTrainingService {

    private final MandatoryTrainingRepository mandatoryTrainingRepository;

    public MandatoryTrainingService(
            MandatoryTrainingRepository mandatoryTrainingRepository) {

        this.mandatoryTrainingRepository = mandatoryTrainingRepository;
    }

    public ApiResponse<MandatoryTrainingResponse>
    createMandatoryTraining(
            MandatoryTrainingRequest request) {

        if (mandatoryTrainingRepository
                .existsByTitleIgnoreCase(
                        request.getTitle())) {

            throw new MandatoryTrainingAlreadyExistsException(
                    "Mandatory training already exists");
        }

        MandatoryTraining training =
                new MandatoryTraining();

        training.setTitle(
                request.getTitle());
        training.setSharePointUrl(
                request.getSharePointUrl());
        training.setActive(
                request.getActive());

        MandatoryTraining saved =
                mandatoryTrainingRepository.save(
                        training);

        return new ApiResponse<>(
                true,
                "Mandatory training created successfully",
                mapToResponse(saved));
    }

    public ApiResponse<List<MandatoryTrainingResponse>>
    getAllMandatoryTrainings() {

        List<MandatoryTrainingResponse> trainings =
                mandatoryTrainingRepository.findAll()
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new ApiResponse<>(
                true,
                "Mandatory trainings fetched successfully",
                trainings);
    }

    public ApiResponse<MandatoryTrainingResponse>
    getMandatoryTrainingById(Long id) {

        MandatoryTraining training =
                mandatoryTrainingRepository.findById(id)
                        .orElseThrow(() ->
                                new MandatoryTrainingNotFoundException(
                                        "Mandatory training not found"));

        return new ApiResponse<>(
                true,
                "Mandatory training fetched successfully",
                mapToResponse(training));
    }

    public ApiResponse<MandatoryTrainingResponse>
    updateMandatoryTraining(
            Long id,
            MandatoryTrainingRequest request) {

        MandatoryTraining training =
                mandatoryTrainingRepository.findById(id)
                        .orElseThrow(() ->
                                new MandatoryTrainingNotFoundException(
                                        "Mandatory training not found"));

        training.setTitle(
                request.getTitle());
        training.setSharePointUrl(
                request.getSharePointUrl());
        training.setActive(
                request.getActive());

        MandatoryTraining updated =
                mandatoryTrainingRepository.save(
                        training);

        return new ApiResponse<>(
                true,
                "Mandatory training updated successfully",
                mapToResponse(updated));
    }

    public ApiResponse<Object>
    deleteMandatoryTraining(Long id) {

        MandatoryTraining training =
                mandatoryTrainingRepository.findById(id)
                        .orElseThrow(() ->
                                new MandatoryTrainingNotFoundException(
                                        "Mandatory training not found"));

        mandatoryTrainingRepository.delete(
                training);

        return new ApiResponse<>(
                true,
                "Mandatory training deleted successfully",
                null);
    }

    private MandatoryTrainingResponse
    mapToResponse(
            MandatoryTraining training) {

        MandatoryTrainingResponse response =
                new MandatoryTrainingResponse();

        response.setId(training.getId());
        response.setTitle(
                training.getTitle());
        response.setSharePointUrl(
                training.getSharePointUrl());
        response.setActive(
                training.getActive());

        return response;
    }
}