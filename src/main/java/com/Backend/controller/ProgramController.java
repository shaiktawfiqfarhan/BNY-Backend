package com.Backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.ProgramRequest;
import com.Backend.dto.ProgramResponse;
import com.Backend.service.ProgramService;

@RestController
@RequestMapping("/api")
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(
            ProgramService programService) {

        this.programService = programService;
    }

    @PostMapping("/admin/programs")
    public ApiResponse<ProgramResponse> createProgram(
            @RequestBody ProgramRequest request) {

        return programService.createProgram(
                request);
    }

    @GetMapping("/programs")
    public ApiResponse<List<ProgramResponse>>
    getAllPrograms() {

        return programService.getAllPrograms();
    }

    @GetMapping("/programs/{id}")
    public ApiResponse<ProgramResponse>
    getProgramById(
            @PathVariable Long id) {

        return programService.getProgramById(
                id);
    }

    @PutMapping("/admin/programs/{id}")
    public ApiResponse<ProgramResponse>
    updateProgram(
            @PathVariable Long id,
            @RequestBody ProgramRequest request) {

        return programService.updateProgram(
                id,
                request);
    }

    @DeleteMapping("/admin/programs/{id}")
    public ApiResponse<Object>
    deleteProgram(
            @PathVariable Long id) {

        return programService.deleteProgram(
                id);
    }
}
