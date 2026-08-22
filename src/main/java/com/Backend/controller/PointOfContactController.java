package com.Backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.PointOfContactRequest;
import com.Backend.dto.PointOfContactResponse;
import com.Backend.service.PointOfContactService;

@RestController
@RequestMapping("/api")
public class PointOfContactController {

    private final PointOfContactService pointOfContactService;

    public PointOfContactController(
            PointOfContactService pointOfContactService) {

        this.pointOfContactService = pointOfContactService;
    }

    @PostMapping("/admin/point-of-contacts")
    public ApiResponse<PointOfContactResponse>
    createPointOfContact(
            @RequestBody PointOfContactRequest request) {

        return pointOfContactService
                .createPointOfContact(request);
    }

    @GetMapping("/point-of-contacts")
    public ApiResponse<List<PointOfContactResponse>>
    getAllPointOfContacts() {

        return pointOfContactService
                .getAllPointOfContacts();
    }

    @GetMapping("/point-of-contacts/{id}")
    public ApiResponse<PointOfContactResponse>
    getPointOfContactById(
            @PathVariable Long id) {

        return pointOfContactService
                .getPointOfContactById(id);
    }

    @PutMapping("/admin/point-of-contacts/{id}")
    public ApiResponse<PointOfContactResponse>
    updatePointOfContact(
            @PathVariable Long id,
            @RequestBody PointOfContactRequest request) {

        return pointOfContactService
                .updatePointOfContact(id, request);
    }

    @DeleteMapping("/admin/point-of-contacts/{id}")
    public ApiResponse<Object>
    deletePointOfContact(
            @PathVariable Long id) {

        return pointOfContactService
                .deletePointOfContact(id);
    }
}