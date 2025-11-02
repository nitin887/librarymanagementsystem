package com.librarymanagementsystem.librarymanagementsystem.controller;

import com.librarymanagementsystem.librarymanagementsystem.config.JwtUtil;
import com.librarymanagementsystem.librarymanagementsystem.dto.AuthRequest;
import com.librarymanagementsystem.librarymanagementsystem.dto.UserCreationRequest;
import com.librarymanagementsystem.librarymanagementsystem.model.User;
import com.librarymanagementsystem.librarymanagementsystem.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public User register(@RequestBody UserCreationRequest userCreationRequest) {
        User user = new User();
        user.setUsername(userCreationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));
        user.setRoles(userCreationRequest.getRoles());
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                User user = userRepository.findByUsername(authRequest.getUsername())
                        .orElseThrow(() -> new AccessDeniedException("User not found"));
                return ResponseEntity.ok(jwtUtil.generateToken(authRequest.getUsername(), user.getRoles()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to login: Authentication failed");
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to login: Invalid username or password");
        }
    }
}
