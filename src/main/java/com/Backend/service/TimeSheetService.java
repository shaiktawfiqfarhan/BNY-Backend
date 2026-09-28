package com.Backend.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.TimeSheetRequest;
import com.Backend.dto.TimeSheetResponse;
import com.Backend.entity.TimeSheetCategory;
import com.Backend.entity.TimeSheetEntry;
import com.Backend.entity.User;
import com.Backend.exception.InvalidTimeSheetException;
import com.Backend.exception.UserNotFoundException;
import com.Backend.repository.TimeSheetRepository;
import com.Backend.repository.UserRepository;

@Service
public class TimeSheetService {

    private final TimeSheetRepository timeSheetRepository;
    private final UserRepository userRepository;

    public TimeSheetService(
            TimeSheetRepository timeSheetRepository,
            UserRepository userRepository) {

        this.timeSheetRepository = timeSheetRepository;
        this.userRepository = userRepository;
    }

    public ApiResponse<TimeSheetResponse> createTimeSheet(
            TimeSheetRequest request,
            String username) {

        User user =
                userRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found"));

        validateRequest(request);

        TimeSheetEntry entry =
                new TimeSheetEntry();

        entry.setEntryDate(
                request.getEntryDate());

        entry.setCategory(
                TimeSheetCategory.valueOf(
                        request.getCategory()));

        entry.setDescription(
                request.getDescription());

        entry.setHours(
                request.getHours());

        entry.setUser(user);

        TimeSheetEntry saved =
                timeSheetRepository.save(entry);

        return new ApiResponse<>(
                true,
                "Timesheet entry created successfully",
                mapToResponse(saved));
    }

    public ApiResponse<List<TimeSheetResponse>>
    getTimeSheetByDate(
            String username,
            LocalDate date) {

        User user =
                userRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found"));

        List<TimeSheetResponse> entries =
                timeSheetRepository
                        .findByUserIdAndEntryDate(
                                user.getId(),
                                date)
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new ApiResponse<>(
                true,
                "Timesheet entries fetched successfully",
                entries);
    }

    public ApiResponse<TimeSheetResponse>
    updateTimeSheet(
            Long id,
            TimeSheetRequest request) {

        TimeSheetEntry entry =
                timeSheetRepository.findById(id)
                        .orElseThrow(() ->
                                
                                new InvalidTimeSheetException(
                                        "Timesheet entry not found"));

        validateRequest(request);

        entry.setEntryDate(
                request.getEntryDate());

        entry.setCategory(
                TimeSheetCategory.valueOf(
                        request.getCategory()));

        entry.setDescription(
                request.getDescription());

        entry.setHours(
                request.getHours());

        TimeSheetEntry updated =
                timeSheetRepository.save(entry);

        return new ApiResponse<>(
                true,
                "Timesheet updated successfully",
                mapToResponse(updated));
    }

    public ApiResponse<Object>
    deleteTimeSheet(
            Long id) {

        TimeSheetEntry entry =
                timeSheetRepository.findById(id)
                        .orElseThrow(() ->
                                new InvalidTimeSheetException(
                                        "Timesheet entry not found"));

        timeSheetRepository.delete(entry);

        return new ApiResponse<>(
                true,
                "Timesheet deleted successfully",
                null);
    }

    private void validateRequest(
            TimeSheetRequest request) {

        if ("OTHERS".equals(
                request.getCategory())
                &&
                (request.getDescription() == null
                        || request.getDescription()
                        .trim()
                        .isEmpty())) {

            throw new InvalidTimeSheetException(
                    "Description is required for OTHERS");
        }

        if (request.getHours() == null
                || request.getHours() <= 0) {

            throw new InvalidTimeSheetException(
                    "Hours must be greater than zero");
        }
    }

    private TimeSheetResponse mapToResponse(
            TimeSheetEntry entry) {

        TimeSheetResponse response =
                new TimeSheetResponse();

        response.setId(entry.getId());
        response.setEntryDate(entry.getEntryDate());
        response.setCategory(entry.getCategory().name());
        response.setDescription(entry.getDescription());
        response.setHours(entry.getHours());

        return response;
    }
}