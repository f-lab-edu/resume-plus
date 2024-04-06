package com.ddoddii.resume.controller;

import com.ddoddii.resume.dto.ResultDTO;
import com.ddoddii.resume.dto.UserDTO;
import com.ddoddii.resume.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity<ResultDTO<String>> signUp(@RequestBody UserDTO user) {
        userService.signUp(user);
        return ResponseEntity.ok(
                ResultDTO.res(HttpStatus.CREATED, HttpStatus.CREATED.toString(), "User created successfully"));
    }

}
