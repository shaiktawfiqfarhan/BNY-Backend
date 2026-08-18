package com.Backend.dto;

import com.Backend.entity.Role;

public class UserProfileResponse {

    private String fullName;
    private String email;
    private String username;
    private Role role;

    public UserProfileResponse(
            String fullName,
            String email,
            String username,
            Role role) {

        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }
}