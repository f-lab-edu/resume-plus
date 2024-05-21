package com.ddoddii.resume.error.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OpenAiErrorCode implements ErrorCode {
    JSON_PARSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Json Parse Error");

    private final HttpStatus httpStatus;
    private final String message;

}
