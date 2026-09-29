package com.Backend.dto;

public class InterviewExperienceRequest {

    private String intervieweeName;

    private Long categoryId;


    public String getIntervieweeName() {
        return intervieweeName;
    }

    public void setIntervieweeName(
            String intervieweeName) {

        this.intervieweeName =
                intervieweeName;
    }


    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(
            Long categoryId) {

        this.categoryId =
                categoryId;
    }
}