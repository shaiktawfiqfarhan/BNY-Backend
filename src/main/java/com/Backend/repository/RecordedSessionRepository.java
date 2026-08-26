package com.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Backend.entity.RecordedSession;

public interface RecordedSessionRepository extends JpaRepository<RecordedSession, Long> {

    List<RecordedSession> findByTrainingId(Long trainingId);
}