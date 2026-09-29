package com.Backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.InterviewCategoryRequest;
import com.Backend.dto.InterviewCategoryResponse;
import com.Backend.service.InterviewCategoryService;

@RestController
@RequestMapping("/api")
public class InterviewCategoryController {

    private final InterviewCategoryService service;

    public InterviewCategoryController(
            InterviewCategoryService service) {

        this.service = service;
    }

    // Public - used by Training page
    @GetMapping("/interview/categories")
    public ApiResponse<List<InterviewCategoryResponse>>
    getAllCategories() {

        return service.getAllCategories();
    }

    // Admin
    @PostMapping("/admin/interview/categories")
    public ApiResponse<InterviewCategoryResponse>
    createCategory(
            @RequestBody InterviewCategoryRequest request) {

        return service.createCategory(request);
    }

    // Admin
    @PutMapping("/admin/interview/categories/{id}")
    public ApiResponse<InterviewCategoryResponse>
    updateCategory(
            @PathVariable Long id,
            @RequestBody InterviewCategoryRequest request) {

        return service.updateCategory(id, request);
    }

    // Admin
    @DeleteMapping("/admin/interview/categories/{id}")
    public ApiResponse<Object>
    deleteCategory(
            @PathVariable Long id) {

        return service.deleteCategory(id);
    }
}