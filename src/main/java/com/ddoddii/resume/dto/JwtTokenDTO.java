package com.ddoddii.resume.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class JwtTokenDTO {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
