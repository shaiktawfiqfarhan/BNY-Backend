package com.Backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.InterviewQuestionRequest;
import com.Backend.dto.InterviewQuestionResponse;
import com.Backend.service.InterviewQuestionService;

@RestController
@RequestMapping("/api")
public class InterviewQuestionController {

    private final InterviewQuestionService service;

    public InterviewQuestionController(
            InterviewQuestionService service) {

        this.service = service;
    }

    // Public - get every interview question
    @GetMapping("/interview/questions")
    public ApiResponse<List<InterviewQuestionResponse>>
    getAllQuestions() {

        return service.getAllQuestions();
    }

    // Public - questions for Java/Mainframe/etc.
    @GetMapping("/interview/questions/category/{categoryId}")
    public ApiResponse<List<InterviewQuestionResponse>>
    getQuestionsByCategory(
            @PathVariable Long categoryId) {

        return service.getQuestionsByCategory(categoryId);
    }

    // Admin only
    @PostMapping("/admin/interview/questions")
    public ApiResponse<InterviewQuestionResponse>
    createQuestion(
            @RequestBody InterviewQuestionRequest request) {

        return service.createQuestion(request);
    }

    // Admin only
    @PutMapping("/admin/interview/questions/{id}")
    public ApiResponse<InterviewQuestionResponse>
    updateQuestion(
            @PathVariable Long id,
            @RequestBody InterviewQuestionRequest request) {

        return service.updateQuestion(id, request);
    }

    // Admin only
    @DeleteMapping("/admin/interview/questions/{id}")
    public ApiResponse<Object>
    deleteQuestion(
            @PathVariable Long id) {

        return service.deleteQuestion(id);
    }
}