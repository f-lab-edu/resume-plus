package com.ddoddii.resume.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserRegisterDto {
    @NotBlank(message = "유저아이디를 입력해주세요")
    @Size(min = 5, max = 15, message = "유저아이디는 5자 이상 15자 이하로 입력해주세요")
    private String username;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "올바른 이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 5, max = 20, message = "비밀번호는 5자 이상 20자 이하로 입력해주세요")
    private String password;
}
