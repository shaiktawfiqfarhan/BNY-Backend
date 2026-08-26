package com.Backend.dto;

public class RecordedSessionRequest {

    private String title;

    private String recordingUrl;

    private Long trainingId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRecordingUrl() {
		return recordingUrl;
	}

	public void setRecordingUrl(String recordingUrl) {
		this.recordingUrl = recordingUrl;
	}

	public Long getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(Long trainingId) {
		this.trainingId = trainingId;
	}

}
