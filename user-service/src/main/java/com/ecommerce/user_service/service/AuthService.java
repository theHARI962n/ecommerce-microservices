package com.ecommerce.user_service.service;


import com.ecommerce.user_service.dto.LoginRequest;
import com.ecommerce.user_service.dto.RegisterRequest;
import com.ecommerce.user_service.entity.User;
import com.ecommerce.user_service.exception.EmailAlreadyExistsException;
import com.ecommerce.user_service.exception.InvalidCredentialsException;
import com.ecommerce.user_service.repository.UserRepository;
import com.ecommerce.user_service.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Registration
    public String register(RegisterRequest request) {
        logger.info("User registration attempt email={}", request.getEmail());
        if (userRepository.existsByEmail(request.getEmail())) {
//            throw new RuntimeException("Email already registered");
            logger.warn("Registration failed email already exists email={}", request.getEmail());
            throw new EmailAlreadyExistsException("Email already registered");
        }



        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        logger.info("User registered successfully email={}", user.getEmail());

        return jwtUtil.generateToken(user.getEmail(), user.getRole());

    }

    // Login
    public String login(LoginRequest request) {

        logger.info("Login attempt email={}", request.getEmail());
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
//            throw new RuntimeException("Invalid email or password");
            logger.warn("Invalid login attempt email={}", request.getEmail());
            throw new InvalidCredentialsException("Invalid email or password");
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            throw new RuntimeException("Invalid email or password");
            throw new InvalidCredentialsException("Invalid email or password");
        }

        logger.info("Login successful email={}", user.getEmail());

        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }
}
