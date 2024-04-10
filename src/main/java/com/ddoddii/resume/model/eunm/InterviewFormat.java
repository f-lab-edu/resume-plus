package com.ddoddii.resume.model.eunm;

import lombok.Getter;

@Getter
public enum InterviewFormat {
    ONE_TO_ONE("1:1"),
    ONE_TO_MANY("1:다"),
    MANY_TO_ONE("다:1"),
    MANY_TO_MANY("다:다");

    private String name;

    InterviewFormat(String name) {
        this.name = name;
    }
}
