package com.Backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.InterviewCategoryRequest;
import com.Backend.dto.InterviewCategoryResponse;
import com.Backend.entity.InterviewCategory;
import com.Backend.repository.InterviewCategoryRepository;

@Service
public class InterviewCategoryService {

    private final InterviewCategoryRepository repository;

    public InterviewCategoryService(
            InterviewCategoryRepository repository) {

        this.repository = repository;
    }

    public ApiResponse<List<InterviewCategoryResponse>> getAllCategories() {

        List<InterviewCategoryResponse> categories =
                repository.findAllByOrderByNameAsc()
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new ApiResponse<>(
                true,
                "Interview categories fetched successfully",
                categories);
    }

    public ApiResponse<InterviewCategoryResponse> createCategory(
            InterviewCategoryRequest request) {

        InterviewCategory category = new InterviewCategory();

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        InterviewCategory saved =
                repository.save(category);

        return new ApiResponse<>(
                true,
                "Interview category created successfully",
                mapToResponse(saved));
    }

    public ApiResponse<InterviewCategoryResponse> updateCategory(
            Long id,
            InterviewCategoryRequest request) {

        InterviewCategory category =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Interview category not found"));

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        InterviewCategory updated =
                repository.save(category);

        return new ApiResponse<>(
                true,
                "Interview category updated successfully",
                mapToResponse(updated));
    }

    public ApiResponse<Object> deleteCategory(Long id) {

        InterviewCategory category =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Interview category not found"));

        repository.delete(category);

        return new ApiResponse<>(
                true,
                "Interview category deleted successfully",
                null);
    }

    private InterviewCategoryResponse mapToResponse(
            InterviewCategory category) {

        InterviewCategoryResponse response =
                new InterviewCategoryResponse();

        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());

        return response;
    }
}