package com.Backend.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.ForgotPasswordRequest;
import com.Backend.dto.LoginRequest;
import com.Backend.dto.LoginResponse;
import com.Backend.dto.RegisterRequest;
import com.Backend.dto.ResetPasswordRequest;
import com.Backend.dto.UpdateUserRequest;
import com.Backend.dto.UserDetailResponse;
import com.Backend.dto.UserProfileResponse;
import com.Backend.dto.UserSummaryResponse;
import com.Backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ApiResponse<Object> register(
            @Valid
            @RequestBody RegisterRequest request) {

        return userService.register(request);
    }
    
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
    		@Valid
            @RequestBody LoginRequest request) {

        return userService.login(request);
    }
    
    @PostMapping("/forgot-password")
    public ApiResponse<Object> forgotPassword(
            @RequestBody ForgotPasswordRequest request) {

        return userService
                .forgotPassword(request.getEmail());
    }
    
    @PostMapping("/reset-password")
    public ApiResponse<Object> resetPassword(
    		@Valid
            @RequestBody ResetPasswordRequest request) {

        return userService.resetPassword(
                request.getToken(),
                request.getNewPassword());
    }
    
    @GetMapping("/profile")
    public ApiResponse<UserProfileResponse> profile(
            Authentication authentication) {

        return userService.getProfile(
                authentication.getName());
    }
    
    @GetMapping("/admin/users")
    public List<UserSummaryResponse>
    getAllUsers() {

        return userService
                .getAllUsers();
    }
    
    @PutMapping("/admin/users/{id}")
    public ApiResponse<Object>
    updateUser(

            @PathVariable Long id,

            @RequestBody
            UpdateUserRequest request) {

        return userService.updateUser(
                id,
                request);
    }
    
    @DeleteMapping("/admin/users/{id}")
    public ApiResponse<Object>
    deleteUser(
            @PathVariable Long id) {

        return userService.deleteUser(
                id);
    }
    
    @GetMapping("/admin/users/{id}")
    public UserDetailResponse
    getUserById(
            @PathVariable Long id) {

        return userService
                .getUserById(id);
    }
}