package com.Backend.dto;

public class MandatoryTrainingResponse {

    private Long id;

    private String title;

    private String sharePointUrl;

    private Boolean active;
    
    private Integer displayOrder;

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

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
    
}