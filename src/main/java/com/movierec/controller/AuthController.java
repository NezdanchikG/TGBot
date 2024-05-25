package com.movierec.controller;

import com.movierec.model.User;
import com.movierec.repository.UserRepository;
import com.movierec.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/signup")
    public Mono<User> signUp(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public Mono<String> login(@RequestBody AuthRequest authRequest) {
        return userRepository.findByUsername(authRequest.getUsername())
                .filter(user -> passwordEncoder.matches(authRequest.getPassword(), user.getPassword()))
                .map(user -> jwtUtil.generateToken(user))
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid credentials")));
    }

    private static class AuthRequest {
        private String username;
        private String password;

        // Add getters
        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        // Add setters if necessary
        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}