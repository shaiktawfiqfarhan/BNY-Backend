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
import com.Backend.dto.ProgramResourceRequest;
import com.Backend.dto.ProgramResourceResponse;
import com.Backend.service.ProgramResourceService;

@RestController
@RequestMapping("/api")
public class ProgramResourceController {

    private final ProgramResourceService
            programResourceService;

    public ProgramResourceController(
            ProgramResourceService programResourceService) {

        this.programResourceService =
                programResourceService;
    }

    @PostMapping("/admin/program-resources")
    public ApiResponse<ProgramResourceResponse>
    createResource(
            @RequestBody
            ProgramResourceRequest request) {

        return programResourceService
                .createResource(request);
    }

    @GetMapping("/programs/{programId}/resources")
    public ApiResponse<List<ProgramResourceResponse>>
    getResourcesByProgram(
            @PathVariable Long programId) {

        return programResourceService
                .getResourcesByProgram(
                        programId);
    }

    @DeleteMapping(
            "/admin/program-resources/{id}")
    public ApiResponse<Object>
    deleteResource(
            @PathVariable Long id) {

        return programResourceService
                .deleteResource(id);
    }
    
    @PutMapping(
            "/admin/program-resources/{id}")
    public ApiResponse<ProgramResourceResponse>
    updateResource(
            @PathVariable Long id,
            @RequestBody
            ProgramResourceRequest request) {

        return programResourceService
                .updateResource(
                        id,
                        request);
    }
}