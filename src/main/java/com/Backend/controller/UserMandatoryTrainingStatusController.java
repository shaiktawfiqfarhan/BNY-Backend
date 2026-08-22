package com.Backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.TrainingStatusRequest;
import com.Backend.dto.UserMandatoryTrainingStatusResponse;
import com.Backend.service.UserMandatoryTrainingStatusService;

@RestController
@RequestMapping("/api/mandatory-trainings")
public class UserMandatoryTrainingStatusController {

    private final UserMandatoryTrainingStatusService
            userMandatoryTrainingStatusService;

    public UserMandatoryTrainingStatusController(
            UserMandatoryTrainingStatusService userMandatoryTrainingStatusService) {

        this.userMandatoryTrainingStatusService =
                userMandatoryTrainingStatusService;
    }

    @PutMapping("/{trainingId}/status")
    public ApiResponse<
            UserMandatoryTrainingStatusResponse>
    updateTrainingStatus(
            @PathVariable Long trainingId,
            @RequestBody TrainingStatusRequest request) {

        return userMandatoryTrainingStatusService
                .updateTrainingStatus(
                        trainingId,
                        request);
    }

    @GetMapping("/status")
    public ApiResponse<
            List<UserMandatoryTrainingStatusResponse>>
    getMyTrainingStatuses() {

        return userMandatoryTrainingStatusService
                .getMyTrainingStatuses();
    }
}