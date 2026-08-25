package com.Backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.Backend.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(
            MethodArgumentNotValidException ex) {

        String errorMessage =
                ex.getBindingResult()
                  .getFieldError()
                  .getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        false,
                        errorMessage,
                        null));
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleUserNotFound(UserNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleEmailNotFound(EmailNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleEmailAlreadyExists(
            EmailAlreadyExistsException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleUsernameAlreadyExists(
            UsernameAlreadyExistsException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleInvalidPassword(
            InvalidPasswordException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleInvalidToken(
            InvalidTokenException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleTokenExpired(
            TokenExpiredException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));
    }
    
    @ExceptionHandler(SectionNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleSectionNotFound(
            SectionNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));
    }
    
    @ExceptionHandler(
            SectionAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleSectionAlreadyExists(
            SectionAlreadyExistsException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));
    }
    
    @ExceptionHandler(
            PointOfContactNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>>
    handlePointOfContactNotFound(
            PointOfContactNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));
    }
    
    @ExceptionHandler(
            PointOfContactAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>>
    handlePointOfContactAlreadyExists(
            PointOfContactAlreadyExistsException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));
    }
    
    @ExceptionHandler(
    		MandatoryTrainingNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleMandatoryTrainingNotFound(
    		MandatoryTrainingNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));
    }
    
    @ExceptionHandler(
    		MandatoryTrainingAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleMandatoryTrainingAlreadyExists(
    		MandatoryTrainingAlreadyExistsException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));
    }
    
    @ExceptionHandler(
    		OnboardingFileNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleOnboardingFileNotFound(
    		OnboardingFileNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));
    }
    
    @ExceptionHandler(
    		OnboardingFileAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleOnboardingFileAlreadyExists(
    		OnboardingFileAlreadyExistsException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));
    }
    
    @ExceptionHandler(
    		TrainingNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>>
    TrainingNotFound(
    		TrainingNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));
    }
    
    @ExceptionHandler(
    		TrainingAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>>
    TrainingAlreadyExists(
    		TrainingAlreadyExistsException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>>
    handleException(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(
                        false,
                        "Something went wrong",
                        null));
    }
    
}
