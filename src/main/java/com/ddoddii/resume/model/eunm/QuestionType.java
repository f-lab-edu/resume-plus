package com.ddoddii.resume.model.eunm;

import lombok.Getter;

@Getter
public enum QuestionType {
    TECH("기술질문"),
    BEHAVIOR("인성질문"),
    INTRODUCE("자기소개"),
    PERSONAL("개인질문");

    private String name;

    QuestionType(String name) {
        this.name = name;
    }
}
