package com.Backend.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.InterviewExperienceRequest;
import com.Backend.dto.InterviewExperienceResponse;
import com.Backend.service.InterviewExperienceService;

@RestController
@RequestMapping("/api")
public class InterviewExperienceController {

    private final InterviewExperienceService experienceService;

    public InterviewExperienceController(
            InterviewExperienceService experienceService) {

        this.experienceService = experienceService;
    }

    // =====================================================
    // USER
    // =====================================================

    @GetMapping("/interview/experiences")
    public ApiResponse<List<InterviewExperienceResponse>> getAllExperiences() {

        return experienceService.getAllExperiences();
    }

    @GetMapping("/interview/experiences/{id}")
    public ApiResponse<InterviewExperienceResponse> getExperienceById(
            @PathVariable Long id) {

        return experienceService.getExperienceById(id);
    }

    @GetMapping("/interview/experiences/category/{categoryId}")
    public ApiResponse<List<InterviewExperienceResponse>> getExperiencesByCategory(
            @PathVariable Long categoryId) {

        return experienceService.getExperiencesByCategory(categoryId);
    }

    // =====================================================
    // USER - WORD DOCUMENT
    // =====================================================

    @GetMapping("/interview/experiences/{id}/document")
    public ResponseEntity<Resource> getDocument(
            @PathVariable Long id) {

        try {

            InterviewExperienceResponse experience =
                    experienceService
                            .getExperienceById(id)
                            .getData();

            if (experience == null ||
                    experience.getDocumentPath() == null) {

                return ResponseEntity.notFound().build();
            }

            Path filePath = Paths.get(
                    System.getProperty("user.dir"),
                    "uploads",
                    "interview-experiences",
                    experience.getDocumentPath()
            ).normalize();

            Resource resource =
                    new UrlResource(filePath.toUri());

            if (!resource.exists() ||
                    !resource.isReadable()) {

                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(
                            MediaType.parseMediaType(
                                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                            )
                    )
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" +
                                    experience.getDocumentName() +
                                    "\""
                    )
                    .body(resource);

        } catch (IOException e) {

            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    // =====================================================
    // ADMIN
    // =====================================================

    @PostMapping("/admin/interview/experiences")
    public ApiResponse<InterviewExperienceResponse> createExperience(
            @RequestBody InterviewExperienceRequest request) {

        return experienceService.createExperience(request);
    }

    @PutMapping("/admin/interview/experiences/{id}")
    public ApiResponse<InterviewExperienceResponse> updateExperience(
            @PathVariable Long id,
            @RequestBody InterviewExperienceRequest request) {

        return experienceService.updateExperience(
                id,
                request
        );
    }

    // =====================================================
    // ADMIN - WORD DOCUMENT
    // =====================================================

    @PostMapping(
            value = "/admin/interview/experiences/{id}/document",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ApiResponse<InterviewExperienceResponse> uploadDocument(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        return experienceService.uploadDocument(
                id,
                file
        );
    }

    @DeleteMapping("/admin/interview/experiences/{id}/document")
    public ApiResponse<Object> deleteDocument(
            @PathVariable Long id) {

        return experienceService.deleteDocument(id);
    }

    // =====================================================
    // ADMIN - EXPERIENCE
    // =====================================================

    @DeleteMapping("/admin/interview/experiences/{id}")
    public ApiResponse<Object> deleteExperience(
            @PathVariable Long id) {

        return experienceService.deleteExperience(id);
    }
}