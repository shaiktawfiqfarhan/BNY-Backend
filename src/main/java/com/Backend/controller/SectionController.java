package com.Backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.SectionRequest;
import com.Backend.dto.SectionResponse;
import com.Backend.service.SectionService;

@RestController
@RequestMapping("/api")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(
            SectionService sectionService) {

        this.sectionService = sectionService;
    }

    @PostMapping("/admin/sections")
    public ApiResponse<SectionResponse> createSection(
            @RequestBody SectionRequest request) {

        return sectionService.createSection(
                request);
    }

    @GetMapping("/sections")
    public ApiResponse<List<SectionResponse>>
    getAllSections() {

        return sectionService.getAllSections();
    }

    @GetMapping("/sections/{id}")
    public ApiResponse<SectionResponse>
    getSectionById(
            @PathVariable Long id) {

        return sectionService.getSectionById(
                id);
    }

    @PutMapping("/admin/sections/{id}")
    public ApiResponse<SectionResponse>
    updateSection(
            @PathVariable Long id,
            @RequestBody SectionRequest request) {

        return sectionService.updateSection(
                id,
                request);
    }

    @DeleteMapping("/admin/sections/{id}")
    public ApiResponse<Object>
    deleteSection(
            @PathVariable Long id) {

        return sectionService.deleteSection(
                id);
    }
}