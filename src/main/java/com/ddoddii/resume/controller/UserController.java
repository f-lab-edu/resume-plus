package com.ddoddii.resume.controller;

import com.ddoddii.resume.dto.JwtTokenDTO;
import com.ddoddii.resume.dto.RefreshTokenRequestDTO;
import com.ddoddii.resume.dto.UserLoginRequestDTO;
import com.ddoddii.resume.dto.UserLoginResponseDTO;
import com.ddoddii.resume.dto.UserSignUpRequestDTO;
import com.ddoddii.resume.dto.UserSignUpResponseDTO;
import com.ddoddii.resume.model.User;
import com.ddoddii.resume.security.TokenProvider;
import com.ddoddii.resume.service.RefreshTokenService;
import com.ddoddii.resume.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/")
    public ResponseEntity<UserSignUpResponseDTO> signUp(@RequestBody @Valid UserSignUpRequestDTO user) {
        UserSignUpResponseDTO response = userService.signUp(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody @Valid UserLoginRequestDTO request) {
        UserLoginResponseDTO response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtTokenDTO> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        JwtTokenDTO jwtTokenDTO = userService.generateNewAccessToken(refreshTokenRequestDTO.getToken());
        return ResponseEntity.ok(jwtTokenDTO);
    }

    @GetMapping("/current-user")
    public ResponseEntity<String> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(user.getEmail());
    }

}
