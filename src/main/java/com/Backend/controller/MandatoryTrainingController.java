package com.Backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.MandatoryTrainingRequest;
import com.Backend.dto.MandatoryTrainingResponse;
import com.Backend.service.MandatoryTrainingService;

@RestController
@RequestMapping("/api")
public class MandatoryTrainingController {

    private final MandatoryTrainingService mandatoryTrainingService;

    public MandatoryTrainingController(
            MandatoryTrainingService mandatoryTrainingService) {

        this.mandatoryTrainingService =
                mandatoryTrainingService;
    }

    @PostMapping("/admin/mandatory-trainings")
    public ApiResponse<MandatoryTrainingResponse>
    createMandatoryTraining(
            @RequestBody MandatoryTrainingRequest request) {

        return mandatoryTrainingService
                .createMandatoryTraining(request);
    }

    @GetMapping("/mandatory-trainings")
    public ApiResponse<List<MandatoryTrainingResponse>>
    getAllMandatoryTrainings() {

        return mandatoryTrainingService
                .getAllMandatoryTrainings();
    }

    @GetMapping("/mandatory-trainings/{id}")
    public ApiResponse<MandatoryTrainingResponse>
    getMandatoryTrainingById(
            @PathVariable Long id) {

        return mandatoryTrainingService
                .getMandatoryTrainingById(id);
    }

    @PutMapping("/admin/mandatory-trainings/{id}")
    public ApiResponse<MandatoryTrainingResponse>
    updateMandatoryTraining(
            @PathVariable Long id,
            @RequestBody MandatoryTrainingRequest request) {

        return mandatoryTrainingService
                .updateMandatoryTraining(
                        id,
                        request);
    }

    @DeleteMapping("/admin/mandatory-trainings/{id}")
    public ApiResponse<Object>
    deleteMandatoryTraining(
            @PathVariable Long id) {

        return mandatoryTrainingService
                .deleteMandatoryTraining(id);
    }
}