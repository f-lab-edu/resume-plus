package com.ddoddii.resume.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
@AllArgsConstructor : 모든 필드 값을 파라미터로 받는 생성자를 생성
@Builder : 빌더 패턴을 활용. DTO 생성 시 빌더 클래스를 자동으로 추가해준다.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userId;
    private String name;
    private String email;
    private String password;
}
