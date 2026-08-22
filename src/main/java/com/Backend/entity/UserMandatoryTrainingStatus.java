package com.Backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_mandatory_training_status")
public class UserMandatoryTrainingStatus extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "mandatory_training_id")
    private MandatoryTraining mandatoryTraining;

    @Enumerated(EnumType.STRING)
    private TrainingStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MandatoryTraining getMandatoryTraining() {
        return mandatoryTraining;
    }

    public void setMandatoryTraining(
            MandatoryTraining mandatoryTraining) {
        this.mandatoryTraining = mandatoryTraining;
    }

    public TrainingStatus getStatus() {
        return status;
    }

    public void setStatus(
            TrainingStatus status) {
        this.status = status;
    }
}