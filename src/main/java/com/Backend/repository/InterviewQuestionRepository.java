package com.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Backend.entity.InterviewQuestion;

public interface InterviewQuestionRepository
        extends JpaRepository<InterviewQuestion, Long> {

    List<InterviewQuestion> findAllByOrderByIdAsc();

    List<InterviewQuestion> findByCategoryIdOrderByIdAsc(Long categoryId);
}