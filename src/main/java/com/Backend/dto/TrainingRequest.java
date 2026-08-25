package com.Backend.dto;

public class TrainingRequest {

    private String title;

    private String description;

    private String sharePointUrl;

    private String trainingType;

    private Boolean active;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSharePointUrl() {
        return sharePointUrl;
    }

    public void setSharePointUrl(
            String sharePointUrl) {
        this.sharePointUrl =
                sharePointUrl;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(
            String trainingType) {
        this.trainingType =
                trainingType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(
            Boolean active) {
        this.active = active;
    }
}