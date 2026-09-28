package com.Backend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.TimeSheetRequest;
import com.Backend.dto.TimeSheetResponse;
import com.Backend.service.TimeSheetService;

@RestController
@RequestMapping("/api/timesheets")
public class TimeSheetController {

    private final TimeSheetService service;

    public TimeSheetController(TimeSheetService service) {
        this.service = service;
    }

    @PostMapping
    public ApiResponse<TimeSheetResponse> create(
            @RequestBody TimeSheetRequest request,
            Authentication authentication) {

        String username = authentication.getName();

        return service.createTimeSheet(
                request,
                username);
    }

    @GetMapping
    public ApiResponse<List<TimeSheetResponse>> getByDate(
            @RequestParam LocalDate date,
            Authentication authentication) {

        String username = authentication.getName();

        return service.getTimeSheetByDate(
                username,
                date);
    }

    @PutMapping("/{id}")
    public ApiResponse<TimeSheetResponse> update(
            @PathVariable Long id,
            @RequestBody TimeSheetRequest request) {

        return service.updateTimeSheet(
                id,
                request);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Object> delete(
            @PathVariable Long id) {

        return service.deleteTimeSheet(id);
    }
}