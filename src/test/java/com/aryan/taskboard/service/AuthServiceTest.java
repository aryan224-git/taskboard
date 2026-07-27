package com.aryan.taskboard.service;

import com.aryan.taskboard.model.User;
import com.aryan.taskboard.repository.UserRepository;
import com.aryan.taskboard.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_shouldSaveUser_whenUsernameNotTaken() {
        when(userRepository.existsByUsername("aryan")).thenReturn(false);
        when(passwordEncoder.encode("test1234")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User result = authService.register("aryan", "aryan@example.com", "test1234");

        assertEquals("aryan", result.getUsername());
        assertEquals("hashedPassword", result.getPasswordHash());
    }

    @Test
    void register_shouldThrow_whenUsernameAlreadyTaken() {
        when(userRepository.existsByUsername("aryan")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                authService.register("aryan", "aryan@example.com", "test1234"));
    }

    @Test
    void login_shouldReturnToken_whenCredentialsValid() {
        User user = new User();
        user.setUsername("aryan");
        user.setPasswordHash("hashedPassword");

        when(userRepository.findByUsername("aryan")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("test1234", "hashedPassword")).thenReturn(true);
        when(jwtUtil.generateToken("aryan")).thenReturn("fake-jwt-token");

        String token = authService.login("aryan", "test1234");

        assertEquals("fake-jwt-token", token);
    }

    @Test
    void login_shouldThrow_whenPasswordWrong() {
        User user = new User();
        user.setUsername("aryan");
        user.setPasswordHash("hashedPassword");

        when(userRepository.findByUsername("aryan")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpassword", "hashedPassword")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () ->
                authService.login("aryan", "wrongpassword"));
    }
}