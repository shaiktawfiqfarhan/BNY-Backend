package com.Backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        this.mandatoryTrainingRepository =
                mandatoryTrainingRepository;
    }

    @Transactional
    public ApiResponse<MandatoryTrainingResponse>
    createMandatoryTraining(
            MandatoryTrainingRequest request) {

        if (mandatoryTrainingRepository
                .existsByTitleIgnoreCase(request.getTitle())) {

            throw new MandatoryTrainingAlreadyExistsException(
                    "Mandatory training already exists");
        }

        List<MandatoryTraining> trainings =
                mandatoryTrainingRepository
                        .findAllByOrderByDisplayOrderAscIdAsc();

        int requestedOrder =
                normalizeRequestedOrder(
                        request.getDisplayOrder(),
                        trainings.size() + 1);

        
        for (MandatoryTraining training : trainings) {

            if (training.getDisplayOrder() >= requestedOrder) {
                training.setDisplayOrder(
                        training.getDisplayOrder() + 1);
            }
        }

        MandatoryTraining training =
                new MandatoryTraining();

        training.setTitle(request.getTitle());
        training.setSharePointUrl(request.getSharePointUrl());
        training.setActive(request.getActive());
        training.setDisplayOrder(requestedOrder);

        mandatoryTrainingRepository.saveAll(trainings);
        MandatoryTraining saved =
                mandatoryTrainingRepository.save(training);

        normalizeDisplayOrders();

        return new ApiResponse<>(
                true,
                "Mandatory training created successfully",
                mapToResponse(saved));
    }

    public ApiResponse<List<MandatoryTrainingResponse>>
    	getAllMandatoryTrainings() {

        List<MandatoryTrainingResponse> trainings =
                mandatoryTrainingRepository
                        .findAllByOrderByDisplayOrderAscIdAsc()
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
                mandatoryTrainingRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new MandatoryTrainingNotFoundException(
                                        "Mandatory training not found"));

        return new ApiResponse<>(
                true,
                "Mandatory training fetched successfully",
                mapToResponse(training));
    }

    @Transactional
    public ApiResponse<MandatoryTrainingResponse>
    updateMandatoryTraining(
            Long id,
            MandatoryTrainingRequest request) {

        MandatoryTraining training =
                mandatoryTrainingRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new MandatoryTrainingNotFoundException(
                                        "Mandatory training not found"));

        List<MandatoryTraining> trainings =
                mandatoryTrainingRepository
                        .findAllByOrderByDisplayOrderAscIdAsc();

        int oldOrder = training.getDisplayOrder();

        int maxOrder = trainings.size();

        int newOrder =
                normalizeRequestedOrder(
                        request.getDisplayOrder(),
                        maxOrder);

        if (oldOrder == newOrder) {

            training.setTitle(request.getTitle());
            training.setSharePointUrl(request.getSharePointUrl());
            training.setActive(request.getActive());

            MandatoryTraining updated =
                    mandatoryTrainingRepository.save(training);

            return new ApiResponse<>(
                    true,
                    "Mandatory training updated successfully",
                    mapToResponse(updated));
        }

        if (newOrder > oldOrder) {

            for (MandatoryTraining item : trainings) {

                if (item.getId().equals(id)) {
                    continue;
                }

                int currentOrder =
                        item.getDisplayOrder();

                if (currentOrder > oldOrder
                        && currentOrder <= newOrder) {

                    item.setDisplayOrder(
                            currentOrder - 1);
                }
            }
        }

        else {

            for (MandatoryTraining item : trainings) {

                if (item.getId().equals(id)) {
                    continue;
                }

                int currentOrder =
                        item.getDisplayOrder();

                if (currentOrder >= newOrder
                        && currentOrder < oldOrder) {

                    item.setDisplayOrder(
                            currentOrder + 1);
                }
            }
        }

        training.setTitle(request.getTitle());
        training.setSharePointUrl(request.getSharePointUrl());
        training.setActive(request.getActive());
        training.setDisplayOrder(newOrder);

        mandatoryTrainingRepository.saveAll(trainings);
        mandatoryTrainingRepository.save(training);

        normalizeDisplayOrders();

        MandatoryTraining updated =
                mandatoryTrainingRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new MandatoryTrainingNotFoundException(
                                        "Mandatory training not found"));

        return new ApiResponse<>(
                true,
                "Mandatory training updated successfully",
                mapToResponse(updated));
    }

    @Transactional
    public ApiResponse<Object>
    deleteMandatoryTraining(Long id) {

        MandatoryTraining training =
                mandatoryTrainingRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new MandatoryTrainingNotFoundException(
                                        "Mandatory training not found"));

        mandatoryTrainingRepository.delete(training);

        mandatoryTrainingRepository.flush();

        normalizeDisplayOrders();

        return new ApiResponse<>(
                true,
                "Mandatory training deleted successfully",
                null);
    }

    private int normalizeRequestedOrder(
            Integer requestedOrder,
            int maxPosition) {

        if (requestedOrder == null) {
            return maxPosition;
        }

        if (requestedOrder < 1) {
            return 1;
        }

        if (requestedOrder > maxPosition) {
            return maxPosition;
        }

        return requestedOrder;
    }

    private void normalizeDisplayOrders() {

        List<MandatoryTraining> trainings =
                mandatoryTrainingRepository
                        .findAllByOrderByDisplayOrderAscIdAsc();

        int order = 1;

        for (MandatoryTraining training : trainings) {

            training.setDisplayOrder(order);

            order++;
        }

        mandatoryTrainingRepository.saveAll(trainings);
    }

    private MandatoryTrainingResponse
    mapToResponse(
            MandatoryTraining training) {

        MandatoryTrainingResponse response =
                new MandatoryTrainingResponse();

        response.setId(training.getId());
        response.setTitle(training.getTitle());
        response.setSharePointUrl(
                training.getSharePointUrl());
        response.setActive(training.getActive());
        response.setDisplayOrder(
                training.getDisplayOrder());

        return response;
    }
}