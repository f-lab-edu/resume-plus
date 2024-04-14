package com.ddoddii.resume.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtTokenDTO {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
