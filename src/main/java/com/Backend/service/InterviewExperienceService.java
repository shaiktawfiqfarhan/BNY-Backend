package com.Backend.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.InterviewExperienceRequest;
import com.Backend.dto.InterviewExperienceResponse;
import com.Backend.entity.InterviewCategory;
import com.Backend.entity.InterviewExperience;
import com.Backend.repository.InterviewCategoryRepository;
import com.Backend.repository.InterviewExperienceRepository;

@Service
public class InterviewExperienceService {

    private final InterviewExperienceRepository
            experienceRepository;

    private final InterviewCategoryRepository
            categoryRepository;
    
    private final Path uploadDirectory =
            Paths.get(System.getProperty("user.dir"),
                    "uploads",
                    "interview-experiences");


    public InterviewExperienceService(
            InterviewExperienceRepository experienceRepository,
            InterviewCategoryRepository categoryRepository) {

        this.experienceRepository =
                experienceRepository;

        this.categoryRepository =
                categoryRepository;
    }


    // =====================================================
    // CREATE
    // =====================================================

    public ApiResponse<InterviewExperienceResponse>
    createExperience(
            InterviewExperienceRequest request) {

        InterviewCategory category =
                categoryRepository.findById(
                        request.getCategoryId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Interview category not found"
                        )
                );


        InterviewExperience experience =
                new InterviewExperience();


        experience.setIntervieweeName(
                request.getIntervieweeName()
        );

        experience.setCategory(
                category
        );


        InterviewExperience saved =
                experienceRepository.save(
                        experience
                );


        return new ApiResponse<>(
                true,
                "Interview experience created successfully",
                mapToResponse(saved)
        );
    }
    
    // =====================================================
    // UPLOAD DOCUMENT
    // =====================================================

    
    
    public ApiResponse<InterviewExperienceResponse> uploadDocument(
            Long id,
            MultipartFile file) {

        try {

            if (file == null || file.isEmpty()) {
                throw new RuntimeException("Please select a Word document");
            }

            String originalFileName = file.getOriginalFilename();

            if (originalFileName == null ||
                    !originalFileName.toLowerCase().endsWith(".docx")) {

                throw new RuntimeException(
                        "Only .docx Word documents are allowed"
                );
            }

            InterviewExperience experience =
                    experienceRepository.findById(id)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Interview experience not found"
                                    )
                            );

            Files.createDirectories(uploadDirectory);

            /*
             * Create a unique filename so two uploaded documents
             * never overwrite each other accidentally.
             */
            String storedFileName =
                    UUID.randomUUID() + ".docx";

            Path targetPath =
                    uploadDirectory.resolve(storedFileName);

            Files.copy(
                    file.getInputStream(),
                    targetPath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            /*
             * Delete the old document if one already exists.
             */
            if (experience.getDocumentPath() != null) {

                Path oldPath =
                        uploadDirectory.resolve(
                                experience.getDocumentPath()
                        );

                Files.deleteIfExists(oldPath);
            }

            experience.setDocumentName(originalFileName);
            experience.setDocumentPath(storedFileName);

            InterviewExperience saved =
                    experienceRepository.save(experience);

            return new ApiResponse<>(
                    true,
                    "Interview questions document uploaded successfully",
                    mapToResponse(saved)
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to save the Word document",
                    e
            );
        }
    }


    // =====================================================
    // GET ALL
    // =====================================================

    public ApiResponse<List<InterviewExperienceResponse>>
    getAllExperiences() {

        List<InterviewExperienceResponse>
                experiences =
                experienceRepository.findAll()
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());


        return new ApiResponse<>(
                true,
                "Interview experiences fetched successfully",
                experiences
        );
    }


    // =====================================================
    // GET BY CATEGORY
    // =====================================================

    public ApiResponse<List<InterviewExperienceResponse>>
    getExperiencesByCategory(
            Long categoryId) {

        List<InterviewExperienceResponse>
                experiences =
                experienceRepository
                        .findByCategoryId(categoryId)
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());


        return new ApiResponse<>(
                true,
                "Interview experiences fetched successfully",
                experiences
        );
    }


    // =====================================================
    // GET BY ID
    // =====================================================

    public ApiResponse<InterviewExperienceResponse>
    getExperienceById(Long id) {

        InterviewExperience experience =
                experienceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Interview experience not found"
                                )
                        );


        return new ApiResponse<>(
                true,
                "Interview experience fetched successfully",
                mapToResponse(experience)
        );
    }


    // =====================================================
    // UPDATE
    // =====================================================

    public ApiResponse<InterviewExperienceResponse>
    updateExperience(
            Long id,
            InterviewExperienceRequest request) {

        InterviewExperience experience =
                experienceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Interview experience not found"
                                )
                        );


        InterviewCategory category =
                categoryRepository.findById(
                        request.getCategoryId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Interview category not found"
                        )
                );


        experience.setIntervieweeName(
                request.getIntervieweeName()
        );

        experience.setCategory(
                category
        );


        InterviewExperience updated =
                experienceRepository.save(
                        experience
                );


        return new ApiResponse<>(
                true,
                "Interview experience updated successfully",
                mapToResponse(updated)
        );
    }
    
    
    // =====================================================
    // DELETE DOCUMENT
    // =====================================================
    
    public ApiResponse<Object> deleteDocument(Long id) {

        try {

            InterviewExperience experience =
                    experienceRepository.findById(id)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Interview experience not found"
                                    )
                            );

            if (experience.getDocumentPath() != null) {

                Path filePath =
                        uploadDirectory.resolve(
                                experience.getDocumentPath()
                        );

                Files.deleteIfExists(filePath);
            }

            experience.setDocumentName(null);
            experience.setDocumentPath(null);

            experienceRepository.save(experience);

            return new ApiResponse<>(
                    true,
                    "Interview questions document deleted successfully",
                    null
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to delete the Word document",
                    e
            );
        }
    }



    // =====================================================
    // DELETE
    // =====================================================

    public ApiResponse<Object>
    deleteExperience(Long id) {

        InterviewExperience experience =
                experienceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Interview experience not found"
                                )
                        );


        experienceRepository.delete(
                experience
        );


        return new ApiResponse<>(
                true,
                "Interview experience deleted successfully",
                null
        );
    }


    // =====================================================
    // MAP RESPONSE
    // =====================================================

    private InterviewExperienceResponse
    mapToResponse(
            InterviewExperience experience) {

        InterviewExperienceResponse response =
                new InterviewExperienceResponse();


        response.setId(
                experience.getId()
        );

        response.setIntervieweeName(
                experience.getIntervieweeName()
        );

        response.setCategoryId(
                experience.getCategory().getId()
        );

        response.setCategoryName(
                experience.getCategory().getName()
        );

        response.setDocumentName(
                experience.getDocumentName()
        );

        response.setDocumentPath(
                experience.getDocumentPath()
        );


        return response;
    }
}