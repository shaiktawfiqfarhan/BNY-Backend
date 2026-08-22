package com.Backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "onboarding_files")
public class OnboardingFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String sharePointUrl;

    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSharePointUrl() {
        return sharePointUrl;
    }

    public void setSharePointUrl(String sharePointUrl) {
        this.sharePointUrl = sharePointUrl;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}