package com.ddoddii.resume.controller;

import com.ddoddii.resume.dto.UserLoginRequestDTO;
import com.ddoddii.resume.dto.UserLoginResponseDTO;
import com.ddoddii.resume.dto.UserSignUpRequestDTO;
import com.ddoddii.resume.dto.UserSignUpResponseDTO;
import com.ddoddii.resume.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDTO> signUp(@RequestBody @Valid UserSignUpRequestDTO user) {
        UserSignUpResponseDTO response = userService.signUp(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody @Valid UserLoginRequestDTO request) {
        UserLoginResponseDTO response = userService.login(request);
        return ResponseEntity.ok(response);
    }

}
