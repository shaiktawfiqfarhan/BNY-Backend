package com.Backend.dto;

import com.Backend.entity.Role;

public class LoginResponse {

    private String token;
    private String username;
    private String fullName;
    private Role role;

    public LoginResponse(
            String token,
            String username,
            String fullName,
            Role role) {

        this.token = token;
        this.username = username;
        this.fullName = fullName;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public Role getRole() {
        return role;
    }
}