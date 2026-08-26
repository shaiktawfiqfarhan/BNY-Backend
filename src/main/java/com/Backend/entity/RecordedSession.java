package com.Backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recorded_sessions")
public class RecordedSession {

    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String recordingUrl;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

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

	public String getRecordingUrl() {
		return recordingUrl;
	}

	public void setRecordingUrl(String recordingUrl) {
		this.recordingUrl = recordingUrl;
	}

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}
    
}