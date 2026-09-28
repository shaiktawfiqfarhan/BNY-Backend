package com.Backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Backend.entity.TimeSheetEntry;

@Repository
public interface TimeSheetRepository
        extends JpaRepository<TimeSheetEntry, Long> {

    List<TimeSheetEntry> findByUserIdAndEntryDate(
            Long userId,
            LocalDate entryDate);
}