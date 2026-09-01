package com.Backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Backend.dto.ApiResponse;
import com.Backend.dto.LoginRequest;
import com.Backend.dto.LoginResponse;
import com.Backend.dto.RegisterRequest;
import com.Backend.dto.UpdateUserRequest;
import com.Backend.dto.UserDetailResponse;
import com.Backend.dto.UserProfileResponse;
import com.Backend.dto.UserSummaryResponse;
import com.Backend.entity.PasswordResetToken;
import com.Backend.entity.Role;
import com.Backend.entity.User;
import com.Backend.exception.EmailAlreadyExistsException;
import com.Backend.exception.EmailNotFoundException;
import com.Backend.exception.InvalidPasswordException;
import com.Backend.exception.InvalidTokenException;
import com.Backend.exception.TokenExpiredException;
import com.Backend.exception.UserNotFoundException;
import com.Backend.exception.UsernameAlreadyExistsException;
import com.Backend.repository.PasswordResetTokenRepository;
import com.Backend.repository.PocAssignmentRepository;
import com.Backend.repository.UserMandatoryTrainingStatusRepository;
import com.Backend.repository.UserRepository;
import com.Backend.security.JwtUtil;

@Service
public class UserService {
	
	@Autowired
	private PasswordResetTokenRepository tokenRepository;

	@Autowired
	private EmailService emailService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final PocAssignmentRepository pocAssignmentRepository;
    private final UserMandatoryTrainingStatusRepository trainingStatusRepository;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            UserMandatoryTrainingStatusRepository trainingStatusRepository, 
            PocAssignmentRepository pocAssignmentRepository) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
		this.pocAssignmentRepository = pocAssignmentRepository;
		this.trainingStatusRepository = trainingStatusRepository;
    }

    public ApiResponse<Object> register(RegisterRequest request)  {

    	if (userRepository.existsByEmail(request.getEmail())) {
    		throw new EmailAlreadyExistsException("Email already exists");
    	}
    	
        if (userRepository.existsByUsername(request.getUsername())) {
        	throw new UsernameAlreadyExistsException("Username already exists");
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());

        user.setPassword(
                passwordEncoder.encode(request.getPassword()));
        
        user.setRole(Role.USER);

        userRepository.save(user);

        return new ApiResponse<>(true, "User Registered Successfully", null);
    }
    
    public ApiResponse<UserProfileResponse> getProfile(
            String username) {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"));

        return new ApiResponse<>(
                true,
                "Profile Fetched Successfully",
                new UserProfileResponse(
                        user.getFullName(),
                        user.getEmail(),
                        user.getUsername(),
                        user.getRole()
                )
        );
    }
    
    public ApiResponse<LoginResponse> login(
            LoginRequest request) {

        User user = userRepository
        		.findByEmail(
        		        request.getEmail())
                .orElse(null);

        if (user == null) {

            throw new UserNotFoundException(
                    "User Not Found");
        }

        boolean isMatch =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword());

        if (!isMatch) {

            throw new InvalidPasswordException(
                    "Invalid Password");
        }

        String token =
                jwtUtil.generateToken(
                        user.getUsername(),
                        user.getRole().name());

        LoginResponse loginResponse =
                new LoginResponse(
                        token,
                        user.getUsername(),
                        user.getFullName(),
                        user.getRole());

        return new ApiResponse<>(true,"Login Successful",loginResponse);
    }
    
    public ApiResponse<Object> forgotPassword(String email) {

        User user = userRepository
                .findByEmail(email)
                .orElse(null);

        if (user == null) {
        	throw new EmailNotFoundException("Email not found");
        }

        String token =
                java.util.UUID.randomUUID().toString();
        
        PasswordResetToken existingToken =
                tokenRepository.findByUser(user)
                        .orElse(null);

        if (existingToken != null) {
            tokenRepository.delete(existingToken);
        }

        PasswordResetToken resetToken =
                new PasswordResetToken();

        resetToken.setToken(token);

        resetToken.setUser(user);

        resetToken.setExpiryDate(
                java.time.LocalDateTime.now()
                        .plusMinutes(15));

        tokenRepository.save(resetToken);

        String resetLink =
                "http://localhost:3000/reset-password?token="
                        + token;

//        emailService.sendEmail(
//                email,
//                "Password Reset",
//                "Click here to reset password:\n" + resetLink);
//
//        return "Password reset email sent";
        System.out.println("=================================");
        System.out.println("RESET LINK:");
        System.out.println(resetLink);
        System.out.println("=================================");

        return new ApiResponse<>(true,"Reset Link Generated",resetLink);
    }
    
    public ApiResponse<Object> resetPassword(
            String token,
            String newPassword) {

        PasswordResetToken resetToken =
                tokenRepository.findByToken(token)
                        .orElse(null);

        if (resetToken == null) {
        	throw new InvalidTokenException("Invalid Token");
        }

        if (resetToken.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

        	throw new TokenExpiredException("Token Expired");
        }

        User user = resetToken.getUser();

        user.setPassword(
                passwordEncoder.encode(newPassword));

        userRepository.save(user);

        tokenRepository.delete(resetToken);

        return new ApiResponse<>(true,"Password Reset Successful",null);
    }
    
    public List<UserSummaryResponse>
    getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> {
                    UserSummaryResponse dto =
                            new UserSummaryResponse();
                    	dto.setId(user.getId());
                    	dto.setName(user.getFullName());
                    	dto.setEmail(user.getEmail());
                    	dto.setUsername(user.getUsername());
                    	dto.setRole(user.getRole());
                    return dto;
                })
                .toList();
    }
    
    public ApiResponse<Object>
    updateUser(
            Long id,
            UpdateUserRequest request) {

        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found"));

        user.setFullName(
                request.getFullName());

        user.setEmail(
                request.getEmail());

        user.setUsername(
                request.getUsername());

        if (request.getRole() != null) {

            user.setRole(
                    Role.valueOf(
                            request.getRole()
                                    .toUpperCase()));
        }

        userRepository.save(user);

        return new ApiResponse<>(
                true,
                "User updated successfully",
                null);
    }
    
    @Transactional
    public ApiResponse<Object> deleteUser(Long id) {
        
        User user =
                userRepository.findById(id)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found"));

        trainingStatusRepository.deleteAllByUserId(id);
        
        pocAssignmentRepository.deleteAllByUserId(id);
        
        userRepository.delete(user);
        
        return new ApiResponse<>(
                true,
                "User deleted successfully",
                null);
    }
    
    public UserDetailResponse
    getUserById(Long id) {

        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() ->
                            new UserNotFoundException(
                                "User not found"));

        UserDetailResponse response =
                new UserDetailResponse();

        response.setId(
                user.getId());

        response.setFullName(
                user.getFullName());

        response.setEmail(
                user.getEmail());

        response.setUsername(
                user.getUsername());

        response.setRole(
                user.getRole());

        return response;
    }
}