package com.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Backend.entity.FAQ;

public interface FAQRepository extends JpaRepository<FAQ, Long> {

    List<FAQ> findAllByOrderByIdAsc();

    List<FAQ> findByCategoryIdOrderByIdAsc(Long categoryId);
}