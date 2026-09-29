package com.Backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.FAQRequest;
import com.Backend.dto.FAQResponse;
import com.Backend.entity.FAQ;
import com.Backend.entity.InterviewCategory;
import com.Backend.repository.FAQRepository;
import com.Backend.repository.InterviewCategoryRepository;

@Service
public class FAQService {

    private final FAQRepository faqRepository;
    private final InterviewCategoryRepository categoryRepository;

    public FAQService(
            FAQRepository faqRepository,
            InterviewCategoryRepository categoryRepository) {

        this.faqRepository = faqRepository;
        this.categoryRepository = categoryRepository;
    }

    // ---------------- GET ALL ----------------

    public ApiResponse<List<FAQResponse>>
    getAllFaqs() {

        List<FAQResponse> faqs =
                faqRepository
                        .findAllByOrderByIdAsc()
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new ApiResponse<>(
                true,
                "FAQs fetched successfully",
                faqs);
    }

    // ---------------- GET BY CATEGORY ----------------

    public ApiResponse<List<FAQResponse>>
    getFaqsByCategory(Long categoryId) {

        List<FAQResponse> faqs =
                faqRepository
                        .findByCategoryIdOrderByIdAsc(categoryId)
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new ApiResponse<>(
                true,
                "FAQs fetched successfully",
                faqs);
    }

    // ---------------- CREATE ----------------

    public ApiResponse<FAQResponse>
    createFaq(FAQRequest request) {

        FAQ faq = new FAQ();

        if (request.getCategoryId() != null) {

            InterviewCategory category =
                    categoryRepository
                            .findById(request.getCategoryId())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Interview category not found"));

            faq.setCategory(category);
        }

        faq.setQuestion(request.getQuestion());
        faq.setAnswer(request.getAnswer());

        FAQ saved =
                faqRepository.save(faq);

        return new ApiResponse<>(
                true,
                "FAQ created successfully",
                mapToResponse(saved));
    }

    // ---------------- UPDATE ----------------

    public ApiResponse<FAQResponse>
    updateFaq(
            Long id,
            FAQRequest request) {

        FAQ faq =
                faqRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "FAQ not found"));

        if (request.getCategoryId() != null) {

            InterviewCategory category =
                    categoryRepository
                            .findById(request.getCategoryId())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Interview category not found"));

            faq.setCategory(category);

        } else {

            faq.setCategory(null);
        }

        faq.setQuestion(request.getQuestion());
        faq.setAnswer(request.getAnswer());

        FAQ updated =
                faqRepository.save(faq);

        return new ApiResponse<>(
                true,
                "FAQ updated successfully",
                mapToResponse(updated));
    }

    // ---------------- DELETE ----------------

    public ApiResponse<Object>
    deleteFaq(Long id) {

        FAQ faq =
                faqRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "FAQ not found"));

        faqRepository.delete(faq);

        return new ApiResponse<>(
                true,
                "FAQ deleted successfully",
                null);
    }

    // ---------------- MAPPER ----------------

    private FAQResponse mapToResponse(FAQ faq) {

        FAQResponse response =
                new FAQResponse();

        response.setId(faq.getId());
        response.setQuestion(faq.getQuestion());
        response.setAnswer(faq.getAnswer());

        if (faq.getCategory() != null) {

            response.setCategoryId(
                    faq.getCategory().getId());

            response.setCategoryName(
                    faq.getCategory().getName());
        }

        return response;
    }
}