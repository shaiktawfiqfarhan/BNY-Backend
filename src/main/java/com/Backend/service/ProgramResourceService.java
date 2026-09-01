package com.Backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.ProgramResourceRequest;
import com.Backend.dto.ProgramResourceResponse;
import com.Backend.entity.Program;
import com.Backend.entity.ProgramResource;
import com.Backend.repository.ProgramRepository;
import com.Backend.repository.ProgramResourceRepository;

@Service
public class ProgramResourceService {

    private final ProgramResourceRepository
            programResourceRepository;

    private final ProgramRepository
            programRepository;

    public ProgramResourceService(
            ProgramResourceRepository programResourceRepository,
            ProgramRepository programRepository) {

        this.programResourceRepository =
                programResourceRepository;

        this.programRepository =
                programRepository;
    }

    public ApiResponse<ProgramResourceResponse>
    createResource(
            ProgramResourceRequest request) {

        Program program =
                programRepository
                        .findById(
                                request.getProgramId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Program not found"));

        ProgramResource resource =
                new ProgramResource();

        resource.setName(
                request.getName());

        resource.setType(
                request.getType());

        resource.setUrl(
                request.getUrl());

        resource.setProgram(
                program);

        ProgramResource saved =
                programResourceRepository
                        .save(resource);

        return new ApiResponse<>(
                true,
                "Resource created successfully",
                mapToResponse(saved));
    }

    public ApiResponse<List<ProgramResourceResponse>>
    getResourcesByProgram(
            Long programId) {

        List<ProgramResourceResponse> resources =
                programResourceRepository
                        .findByProgramId(programId)
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new ApiResponse<>(
                true,
                "Resources fetched successfully",
                resources);
    }

    public ApiResponse<Object>
    deleteResource(
            Long id) {

        programResourceRepository
                .deleteById(id);

        return new ApiResponse<>(
                true,
                "Resource deleted successfully",
                null);
    }

    private ProgramResourceResponse
    mapToResponse(
            ProgramResource resource) {

        ProgramResourceResponse response =
                new ProgramResourceResponse();

        response.setId(
                resource.getId());

        response.setName(
                resource.getName());

        response.setType(
                resource.getType());

        response.setUrl(
                resource.getUrl());

        return response;
    }
    
    public ApiResponse<ProgramResourceResponse>
    updateResource(
            Long id,
            ProgramResourceRequest request) {

        ProgramResource resource =
                programResourceRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Resource not found"));

        resource.setName(
                request.getName());

        resource.setType(
                request.getType());

        resource.setUrl(
                request.getUrl());

        ProgramResource updated =
                programResourceRepository
                        .save(resource);

        return new ApiResponse<>(
                true,
                "Resource updated successfully",
                mapToResponse(updated));
    }
}