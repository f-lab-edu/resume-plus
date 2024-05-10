package com.ddoddii.resume.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserLoginResponseDTO {
    private long userId;
    private String name;
    private String email;
    private String pictureUrl;
    private JwtTokenDTO token;
}