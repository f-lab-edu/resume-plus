package com.ddoddii.resume.model.eunm;

import lombok.Getter;

@Getter
public enum InterviewRound {
    
    FIRST("1차 면접"),
    SECOND("2차 면접"),
    THIRD("3차 면접"),
    FOURTH("4차 면접");
    private String name;

    InterviewRound(String name) {
        this.name = name;
    }

}
