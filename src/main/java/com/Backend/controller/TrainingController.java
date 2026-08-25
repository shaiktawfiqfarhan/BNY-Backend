package com.Backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.TrainingRequest;
import com.Backend.dto.TrainingResponse;
import com.Backend.service.TrainingService;

@RestController
@RequestMapping("/api")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(
            TrainingService trainingService) {

        this.trainingService =
                trainingService;
    }

    @PostMapping("/admin/trainings")
    public ApiResponse<TrainingResponse>
    createTraining(
            @RequestBody
            TrainingRequest request) {

        return trainingService
                .createTraining(
                        request);
    }

    @GetMapping("/trainings")
    public ApiResponse<List<TrainingResponse>>
    getAllTrainings() {

        return trainingService
                .getAllTrainings();
    }

    @GetMapping("/trainings/{id}")
    public ApiResponse<TrainingResponse>
    getTrainingById(
            @PathVariable Long id) {

        return trainingService
                .getTrainingById(id);
    }

    @PutMapping("/admin/trainings/{id}")
    public ApiResponse<TrainingResponse>
    updateTraining(
            @PathVariable Long id,
            @RequestBody
            TrainingRequest request) {

        return trainingService
                .updateTraining(
                        id,
                        request);
    }

    @DeleteMapping("/admin/trainings/{id}")
    public ApiResponse<Object>
    deleteTraining(
            @PathVariable Long id) {

        return trainingService
                .deleteTraining(id);
    }

    @GetMapping("/trainings/type/{type}")
    public ApiResponse<List<TrainingResponse>>
    getTrainingsByType(
            @PathVariable String type) {

        return trainingService
                .getTrainingsByType(
                        type);
    }
}