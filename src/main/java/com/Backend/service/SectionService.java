package com.Backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.SectionRequest;
import com.Backend.dto.SectionResponse;
import com.Backend.entity.Section;
import com.Backend.exception.SectionAlreadyExistsException;
import com.Backend.exception.SectionNotFoundException;
import com.Backend.repository.SectionRepository;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    public SectionService(
            SectionRepository sectionRepository) {

        this.sectionRepository = sectionRepository;
    }

    public ApiResponse<SectionResponse> createSection(
            SectionRequest request) {

        if (sectionRepository.existsByNameIgnoreCase(
                request.getName())) {

            throw new SectionAlreadyExistsException(
                    "Section already exists");
        }

        Section section = new Section();

        section.setName(request.getName());
        section.setSharePointUrl(
                request.getSharePointUrl());
        section.setDisplayOrder(
                request.getDisplayOrder());
        section.setActive(
                request.getActive());

        Section savedSection =
                sectionRepository.save(section);

        return new ApiResponse<>(
                true,
                "Section created successfully",
                mapToResponse(savedSection));
    }

    public ApiResponse<List<SectionResponse>>
    getAllSections() {

        List<SectionResponse> sections =
                sectionRepository.findAll()
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new ApiResponse<>(
                true,
                "Sections fetched successfully",
                sections);
    }

    public ApiResponse<SectionResponse>
    getSectionById(Long id) {

        Section section =
                sectionRepository.findById(id)
                        .orElseThrow(() ->
                                new SectionNotFoundException(
                                        "Section not found"));

        return new ApiResponse<>(
                true,
                "Section fetched successfully",
                mapToResponse(section));
    }

    public ApiResponse<SectionResponse>
    updateSection(
            Long id,
            SectionRequest request) {

        Section section =
                sectionRepository.findById(id)
                        .orElseThrow(() ->
                                new SectionNotFoundException(
                                        "Section not found"));

        section.setName(request.getName());
        section.setSharePointUrl(
                request.getSharePointUrl());
        section.setDisplayOrder(
                request.getDisplayOrder());
        section.setActive(
                request.getActive());

        Section updatedSection =
                sectionRepository.save(section);

        return new ApiResponse<>(
                true,
                "Section updated successfully",
                mapToResponse(updatedSection));
    }

    public ApiResponse<Object>
    deleteSection(Long id) {

        Section section =
                sectionRepository.findById(id)
                        .orElseThrow(() ->
                                new SectionNotFoundException(
                                        "Section not found"));

        sectionRepository.delete(section);

        return new ApiResponse<>(
                true,
                "Section deleted successfully",
                null);
    }

    private SectionResponse mapToResponse(
            Section section) {

        SectionResponse response =
                new SectionResponse();

        response.setId(section.getId());
        response.setName(section.getName());
        response.setSharePointUrl(
                section.getSharePointUrl());
        response.setDisplayOrder(
                section.getDisplayOrder());
        response.setActive(
                section.getActive());

        return response;
    }
}