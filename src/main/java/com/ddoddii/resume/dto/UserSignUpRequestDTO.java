package com.ddoddii.resume.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
@AllArgsConstructor : 모든 필드 값을 파라미터로 받는 생성자를 생성한다.
@Builder : 빌더 패턴을 활용. DTO 생성 시 빌더 클래스를 자동으로 추가해준다.
@NotBlank : null 과 "" 과 " " 모두 허용하지 않는다.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequestDTO {

    private String name;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    private String pictureUrl;
}
