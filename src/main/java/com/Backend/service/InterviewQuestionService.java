package com.Backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.InterviewQuestionRequest;
import com.Backend.dto.InterviewQuestionResponse;
import com.Backend.entity.InterviewCategory;
import com.Backend.entity.InterviewQuestion;
import com.Backend.repository.InterviewCategoryRepository;
import com.Backend.repository.InterviewQuestionRepository;

@Service
public class InterviewQuestionService {

    private final InterviewQuestionRepository questionRepository;
    private final InterviewCategoryRepository categoryRepository;

    public InterviewQuestionService(
            InterviewQuestionRepository questionRepository,
            InterviewCategoryRepository categoryRepository) {

        this.questionRepository = questionRepository;
        this.categoryRepository = categoryRepository;
    }

    // ---------------- GET ALL ----------------

    public ApiResponse<List<InterviewQuestionResponse>>
    getAllQuestions() {

        List<InterviewQuestionResponse> questions =
                questionRepository
                        .findAllByOrderByIdAsc()
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new ApiResponse<>(
                true,
                "Interview questions fetched successfully",
                questions);
    }

    // ---------------- GET BY CATEGORY ----------------

    public ApiResponse<List<InterviewQuestionResponse>>
    getQuestionsByCategory(Long categoryId) {

        List<InterviewQuestionResponse> questions =
                questionRepository
                        .findByCategoryIdOrderByIdAsc(categoryId)
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new ApiResponse<>(
                true,
                "Interview questions fetched successfully",
                questions);
    }

    // ---------------- CREATE ----------------

    public ApiResponse<InterviewQuestionResponse>
    createQuestion(InterviewQuestionRequest request) {

        InterviewCategory category =
                categoryRepository
                        .findById(request.getCategoryId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Interview category not found"));

        InterviewQuestion question =
                new InterviewQuestion();

        question.setCategory(category);
        question.setQuestion(request.getQuestion());

        InterviewQuestion saved =
                questionRepository.save(question);

        return new ApiResponse<>(
                true,
                "Interview question created successfully",
                mapToResponse(saved));
    }

    // ---------------- UPDATE ----------------

    public ApiResponse<InterviewQuestionResponse>
    updateQuestion(
            Long id,
            InterviewQuestionRequest request) {

        InterviewQuestion question =
                questionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Interview question not found"));

        InterviewCategory category =
                categoryRepository
                        .findById(request.getCategoryId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Interview category not found"));

        question.setCategory(category);
        question.setQuestion(request.getQuestion());

        InterviewQuestion updated =
                questionRepository.save(question);

        return new ApiResponse<>(
                true,
                "Interview question updated successfully",
                mapToResponse(updated));
    }

    // ---------------- DELETE ----------------

    public ApiResponse<Object>
    deleteQuestion(Long id) {

        InterviewQuestion question =
                questionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Interview question not found"));

        questionRepository.delete(question);

        return new ApiResponse<>(
                true,
                "Interview question deleted successfully",
                null);
    }

    // ---------------- MAPPER ----------------

    private InterviewQuestionResponse mapToResponse(
            InterviewQuestion question) {

        InterviewQuestionResponse response =
                new InterviewQuestionResponse();

        response.setId(question.getId());
        response.setQuestion(question.getQuestion());

        if (question.getCategory() != null) {

            response.setCategoryId(
                    question.getCategory().getId());

            response.setCategoryName(
                    question.getCategory().getName());
        }

        return response;
    }
}