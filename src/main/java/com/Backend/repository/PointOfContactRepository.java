package com.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Backend.entity.PointOfContact;

public interface PointOfContactRepository
        extends JpaRepository<PointOfContact, Long> {

    boolean existsByEmailIgnoreCase(String email);
}