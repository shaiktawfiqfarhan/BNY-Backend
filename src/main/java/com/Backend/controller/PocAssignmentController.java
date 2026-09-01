package com.Backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Backend.dto.PocAssignmentRequest;
import com.Backend.service.PocAssignmentService;

@RestController
@RequestMapping("/api/admin/poc-assignments")
public class PocAssignmentController {

    private final PocAssignmentService
            pocAssignmentService;

    public PocAssignmentController(
            PocAssignmentService pocAssignmentService) {

        this.pocAssignmentService =
                pocAssignmentService;
    }
    
    @GetMapping("/my-pocs")
    public ResponseEntity<?> getMyPocs() {

        return ResponseEntity.ok(
                pocAssignmentService
                        .getMyPocs());
    }

    @PostMapping
    public ResponseEntity<String> assignPoc(
            @RequestBody
            PocAssignmentRequest request) {

        pocAssignmentService
                .assignPocToUser(
                        request);

        return ResponseEntity.ok(
                "POC assigned successfully");
    }
    
    @GetMapping("/{pocId}/employees")
    public ResponseEntity<?> getEmployees(
            @PathVariable Long pocId) {

        return ResponseEntity.ok(
                pocAssignmentService
                        .getAssignedEmployees(
                                pocId));
    }
    
    @DeleteMapping("/{pocId}/employees/{userId}")
    public ResponseEntity<String>
    removeEmployee(
            @PathVariable Long pocId,
            @PathVariable Long userId) {

        pocAssignmentService
                .removeEmployeeFromPoc(
                        userId,
                        pocId);

        return ResponseEntity.ok(
                "Employee removed successfully");
    }
}