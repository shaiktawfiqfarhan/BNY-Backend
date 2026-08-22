package com.Backend.dto;

import com.Backend.entity.TrainingStatus;

public class UserMandatoryTrainingStatusResponse {

    private Long trainingId;

    private String trainingTitle;

    private TrainingStatus status;

    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }

    public String getTrainingTitle() {
        return trainingTitle;
    }

    public void setTrainingTitle(String trainingTitle) {
        this.trainingTitle = trainingTitle;
    }

    public TrainingStatus getStatus() {
        return status;
    }

    public void setStatus(TrainingStatus status) {
        this.status = status;
    }
}