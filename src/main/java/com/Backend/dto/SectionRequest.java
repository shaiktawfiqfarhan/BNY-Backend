package com.Backend.dto;

public class SectionRequest {
	private String name;
	private String sharePointUrl;
	private Integer displayOrder;
	private Boolean active;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSharePointUrl() {
		return sharePointUrl;
	}
	public void setSharePointUrl(String sharePointUrl) {
		this.sharePointUrl = sharePointUrl;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
}
