package com.Backend.dto;

public class MandatoryTrainingRequest {

    private String title;

    private String sharePointUrl;

    private Boolean active;

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