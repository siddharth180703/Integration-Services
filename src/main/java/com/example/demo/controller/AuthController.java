package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.SignupRequest;
import com.example.demo.model.AppUser;
import com.example.demo.repository.UserRepository;
import com.example.demo.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        if (userRepo.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        // Ensure you are using the same passwordEncoder that is configured in SecurityConfig
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        AppUser user = AppUser.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .role(request.getRole())
                .build();
        userRepo.save(user);

        System.out.println("--- Signup Successful: " + user.getUsername() + " ---");
        return ResponseEntity.ok("Signup successful");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

        System.out.println("--- Login Attempt for: " + request.getUsername() + " ---");

        // 1. User Lookup
        AppUser user = userRepo.findByUsername(request.getUsername())
                .orElse(null);

        if (user == null) {
            System.out.println("Login Failed: User not found for username: " + request.getUsername());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credentials (User)");
        }

        // 2. Password Verification
        // req.getPassword() is the plain text password from the request
        // user.getPassword() is the stored, encoded password from the database
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            System.out.println("Login Failed: Password mismatch for user: " + request.getUsername());
            // Debugging: Print a segment of the stored hash
            System.out.println("Stored Hash Segment: " + user.getPassword().substring(0, 10) + "...");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credentials (Password)");
        }

        // 3. Success: Generate Token
        String token = jwtService.generateToken(user.getUsername(), user.getRole());
        System.out.println("Login Successful. Token generated.");

        // 4. Create HttpOnly Cookie
        ResponseCookie cookie = ResponseCookie.from("accessToken", token)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(24 * 60 * 60)
                .build();

        // 5. Send Response
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Login successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        ResponseCookie cookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0) // Expire immediately
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Logged out successfully");
    }
}