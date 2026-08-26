package com.Backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.RecordedSessionRequest;
import com.Backend.dto.RecordedSessionResponse;
import com.Backend.service.RecordedSessionService;

@RestController
@RequestMapping("/api")
public class RecordedSessionController {

    private final RecordedSessionService
            recordedSessionService;

    public RecordedSessionController(
            RecordedSessionService recordedSessionService) {

        this.recordedSessionService =
                recordedSessionService;
    }

    @PostMapping("/admin/recorded-sessions")
    public ApiResponse<RecordedSessionResponse>
    createRecordedSession(
            @RequestBody
            RecordedSessionRequest request) {

        return recordedSessionService
                .createRecordedSession(
                        request);
    }

    @GetMapping(
            "/trainings/{trainingId}/recorded-sessions")
    public ApiResponse<
            List<RecordedSessionResponse>>
    getSessionsByTraining(
            @PathVariable
            Long trainingId) {

        return recordedSessionService
                .getSessionsByTraining(
                        trainingId);
    }

    @DeleteMapping(
            "/admin/recorded-sessions/{id}")
    public ApiResponse<Object>
    deleteRecordedSession(
            @PathVariable Long id) {

        return recordedSessionService
                .deleteRecordedSession(
                        id);
    }
}