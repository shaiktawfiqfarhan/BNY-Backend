package com.Backend.dto;

public class InterviewExperienceResponse {

    private Long id;

    private String intervieweeName;

    private Long categoryId;

    private String categoryName;

    private String documentName;

    private String documentPath;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


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


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(
            String categoryName) {

        this.categoryName =
                categoryName;
    }


    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(
            String documentName) {

        this.documentName =
                documentName;
    }


    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(
            String documentPath) {

        this.documentPath =
                documentPath;
    }
}