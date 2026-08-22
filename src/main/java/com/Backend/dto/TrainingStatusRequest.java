package com.Backend.dto;

import com.Backend.entity.TrainingStatus;

public class TrainingStatusRequest {

    private TrainingStatus status;

    public TrainingStatus getStatus() {
        return status;
    }

    public void setStatus(TrainingStatus status) {
        this.status = status;
    }
}