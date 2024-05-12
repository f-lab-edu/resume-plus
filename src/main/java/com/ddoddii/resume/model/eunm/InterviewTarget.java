package com.ddoddii.resume.model.eunm;

import lombok.Getter;

@Getter
public enum InterviewTarget {
    AI("AI"),
    WORKING_STAFF("실무진"),
    HR("인사팀"),
    CEO("임원진");

    private String name;

    InterviewTarget(String name) {
        this.name = name;
    }
}
