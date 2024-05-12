package com.ddoddii.resume.model.eunm;

import lombok.Getter;

@Getter
public enum Position {
    FRONTEND_DEVELOPER("프런트엔드 개발자"),
    BACKEND_DEVELOPER("백엔드 개발자"),
    ML_DEVELOPER("머신러닝 엔지니어"),
    MOBILE_DEVELOPER("모바일 개발자"),
    INFRA("인프라"),
    SECURITY("보안"),
    MANAGEMENT("기획자");

    private String name;

    Position(String name) {
        this.name = name;
    }
}
