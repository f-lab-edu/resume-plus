package com.ddoddii.resume.error.exception;

import com.ddoddii.resume.error.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotResumeOwnerException extends RuntimeException {
    private final ErrorCode errorCode;
}
