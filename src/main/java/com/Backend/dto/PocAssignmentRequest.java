package com.Backend.dto;

public class PocAssignmentRequest {
	
	private Long userId;
	private Long pointOfContactId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getPointOfContactId() {
		return pointOfContactId;
	}
	public void setPointOfContactId(Long pointOfContactId) {
		this.pointOfContactId = pointOfContactId;
	}
	
	
}
