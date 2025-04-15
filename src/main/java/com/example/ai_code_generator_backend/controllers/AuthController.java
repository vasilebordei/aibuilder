package com.example.ai_code_generator_backend.controllers;

import com.example.ai_code_generator_backend.config.JwtUtil;
import com.example.ai_code_generator_backend.dtos.LoginRequestDTO;
import com.example.ai_code_generator_backend.dtos.LoginResponseDTO;
import com.example.ai_code_generator_backend.dtos.RegistrationRequestDTO;
import com.example.ai_code_generator_backend.models.User;
import com.example.ai_code_generator_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username(),
                            loginRequest.password()
                    )
            );
            // Generate JWT token after successful authentication
            String token = jwtUtil.generateToken(loginRequest.username());
            // Return token and a success message in the response body
            LoginResponseDTO response = new LoginResponseDTO(token, "Login successful");
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequestDTO registrationRequest) {
        if (userService.getUserByUsername(registrationRequest.username()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already taken");
        }
        User user = new User(
                registrationRequest.username(),
                registrationRequest.password(),
                registrationRequest.email()
        );
        userService.createUser(user);
        return ResponseEntity.ok("User registered successfully");
    }
}