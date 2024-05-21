package com.ddoddii.resume.error.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    DUPLICATE_USER(HttpStatus.CONFLICT, "User with same id already exists"),
    NOT_EXIST_USER(HttpStatus.NOT_FOUND, "User not found"),
    BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Bad Credentials");


    private final HttpStatus httpStatus;
    private final String message;

}
