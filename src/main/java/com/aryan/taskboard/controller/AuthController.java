package com.aryan.taskboard.controller;

import com.aryan.taskboard.model.User;
import com.aryan.taskboard.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        User user = authService.register(body.get("username"), body.get("email"), body.get("password"));
        return ResponseEntity.ok(Map.of("id", user.getId(), "username", user.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String token = authService.login(body.get("username"), body.get("password"));
        return ResponseEntity.ok(Map.of("token", token));
    }
}