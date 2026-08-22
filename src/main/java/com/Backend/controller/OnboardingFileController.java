package com.Backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.OnboardingFileRequest;
import com.Backend.dto.OnboardingFileResponse;
import com.Backend.service.OnboardingFileService;

@RestController
@RequestMapping("/api")
public class OnboardingFileController {

    private final OnboardingFileService
            onboardingFileService;

    public OnboardingFileController(
            OnboardingFileService onboardingFileService) {

        this.onboardingFileService =
                onboardingFileService;
    }

    @PostMapping("/admin/onboarding-files")
    public ApiResponse<OnboardingFileResponse>
    createOnboardingFile(
            @RequestBody OnboardingFileRequest request) {

        return onboardingFileService
                .createOnboardingFile(request);
    }

    @GetMapping("/onboarding-files")
    public ApiResponse<List<OnboardingFileResponse>>
    getAllOnboardingFiles() {

        return onboardingFileService
                .getAllOnboardingFiles();
    }

    @GetMapping("/onboarding-files/{id}")
    public ApiResponse<OnboardingFileResponse>
    getOnboardingFileById(
            @PathVariable Long id) {

        return onboardingFileService
                .getOnboardingFileById(id);
    }

    @PutMapping("/admin/onboarding-files/{id}")
    public ApiResponse<OnboardingFileResponse>
    updateOnboardingFile(
            @PathVariable Long id,
            @RequestBody OnboardingFileRequest request) {

        return onboardingFileService
                .updateOnboardingFile(
                        id,
                        request);
    }

    @DeleteMapping("/admin/onboarding-files/{id}")
    public ApiResponse<Object>
    deleteOnboardingFile(
            @PathVariable Long id) {

        return onboardingFileService
                .deleteOnboardingFile(id);
    }
}