package com.Backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "interview_experience")
public class InterviewExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "interviewee_name", nullable = false, length = 150)
    private String intervieweeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private InterviewCategory category;

    @Column(name = "document_name", length = 255)
    private String documentName;

    @Column(name = "document_path", length = 500)
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


    public InterviewCategory getCategory() {
        return category;
    }

    public void setCategory(
            InterviewCategory category) {

        this.category = category;
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