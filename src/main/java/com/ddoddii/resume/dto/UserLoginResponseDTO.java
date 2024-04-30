package com.ddoddii.resume.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginResponseDTO {
    private String name;
    private String email;
    private String pictureUrl;
    private JwtTokenDTO token;
}