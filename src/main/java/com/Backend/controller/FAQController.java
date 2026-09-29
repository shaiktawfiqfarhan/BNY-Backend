package com.Backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.FAQRequest;
import com.Backend.dto.FAQResponse;
import com.Backend.service.FAQService;

@RestController
@RequestMapping("/api")
public class FAQController {

    private final FAQService service;

    public FAQController(
            FAQService service) {

        this.service = service;
    }

    // Public - get all FAQs
    @GetMapping("/interview/faqs")
    public ApiResponse<List<FAQResponse>>
    getAllFaqs() {

        return service.getAllFaqs();
    }

    // Public - get FAQs for a category
    @GetMapping("/interview/faqs/category/{categoryId}")
    public ApiResponse<List<FAQResponse>>
    getFaqsByCategory(
            @PathVariable Long categoryId) {

        return service.getFaqsByCategory(categoryId);
    }

    // Admin only
    @PostMapping("/admin/interview/faqs")
    public ApiResponse<FAQResponse>
    createFaq(
            @RequestBody FAQRequest request) {

        return service.createFaq(request);
    }

    // Admin only
    @PutMapping("/admin/interview/faqs/{id}")
    public ApiResponse<FAQResponse>
    updateFaq(
            @PathVariable Long id,
            @RequestBody FAQRequest request) {

        return service.updateFaq(id, request);
    }

    // Admin only
    @DeleteMapping("/admin/interview/faqs/{id}")
    public ApiResponse<Object>
    deleteFaq(
            @PathVariable Long id) {

        return service.deleteFaq(id);
    }
}