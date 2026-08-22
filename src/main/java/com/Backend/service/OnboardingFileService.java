package com.Backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.OnboardingFileRequest;
import com.Backend.dto.OnboardingFileResponse;
import com.Backend.entity.OnboardingFile;
import com.Backend.exception.OnboardingFileAlreadyExistsException;
import com.Backend.exception.OnboardingFileNotFoundException;
import com.Backend.repository.OnboardingFileRepository;

@Service
public class OnboardingFileService {

    private final OnboardingFileRepository onboardingFileRepository;

    public OnboardingFileService(
            OnboardingFileRepository onboardingFileRepository) {

        this.onboardingFileRepository =
                onboardingFileRepository;
    }

    public ApiResponse<OnboardingFileResponse>
    createOnboardingFile(
            OnboardingFileRequest request) {

        if (onboardingFileRepository
                .existsByTitleIgnoreCase(
                        request.getTitle())) {

            throw new OnboardingFileAlreadyExistsException(
                    "Onboarding file already exists");
        }

        OnboardingFile file =
                new OnboardingFile();

        file.setTitle(request.getTitle());
        file.setSharePointUrl(
                request.getSharePointUrl());
        file.setActive(
                request.getActive());

        OnboardingFile saved =
                onboardingFileRepository.save(file);

        return new ApiResponse<>(
                true,
                "Onboarding file created successfully",
                mapToResponse(saved));
    }

    public ApiResponse<List<OnboardingFileResponse>>
    getAllOnboardingFiles() {

        List<OnboardingFileResponse> files =
                onboardingFileRepository.findAll()
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new ApiResponse<>(
                true,
                "Onboarding files fetched successfully",
                files);
    }

    public ApiResponse<OnboardingFileResponse>
    getOnboardingFileById(Long id) {

        OnboardingFile file =
                onboardingFileRepository.findById(id)
                        .orElseThrow(() ->
                                new OnboardingFileNotFoundException(
                                        "Onboarding file not found"));

        return new ApiResponse<>(
                true,
                "Onboarding file fetched successfully",
                mapToResponse(file));
    }

    public ApiResponse<OnboardingFileResponse>
    updateOnboardingFile(
            Long id,
            OnboardingFileRequest request) {

        OnboardingFile file =
                onboardingFileRepository.findById(id)
                        .orElseThrow(() ->
                                new OnboardingFileNotFoundException(
                                        "Onboarding file not found"));

        file.setTitle(request.getTitle());
        file.setSharePointUrl(
                request.getSharePointUrl());
        file.setActive(
                request.getActive());

        OnboardingFile updated =
                onboardingFileRepository.save(file);

        return new ApiResponse<>(
                true,
                "Onboarding file updated successfully",
                mapToResponse(updated));
    }

    public ApiResponse<Object>
    deleteOnboardingFile(Long id) {

        OnboardingFile file =
                onboardingFileRepository.findById(id)
                        .orElseThrow(() ->
                                new OnboardingFileNotFoundException(
                                        "Onboarding file not found"));

        onboardingFileRepository.delete(file);

        return new ApiResponse<>(
                true,
                "Onboarding file deleted successfully",
                null);
    }

    private OnboardingFileResponse mapToResponse(
            OnboardingFile file) {

        OnboardingFileResponse response =
                new OnboardingFileResponse();

        response.setId(file.getId());
        response.setTitle(file.getTitle());
        response.setSharePointUrl(
                file.getSharePointUrl());
        response.setActive(file.getActive());

        return response;
    }
}