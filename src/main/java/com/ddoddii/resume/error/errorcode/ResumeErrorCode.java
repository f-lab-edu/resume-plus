package com.ddoddii.resume.error.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResumeErrorCode implements ErrorCode {
    NOT_EXIST_RESUME(HttpStatus.NOT_FOUND, "User not found");

    private final HttpStatus httpStatus;
    private final String message;
}
