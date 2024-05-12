package com.ddoddii.resume.model.eunm;

import lombok.Getter;

@Getter
public enum InterviewType {
    GENERAL("일반면접"),
    PT("PT면접"),
    DEBATE("토론면접");

    private String name;

    InterviewType(String name) {
        this.name = name;
    }
}
