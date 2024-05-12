package com.ddoddii.resume.model.eunm;

import lombok.Getter;

@Getter
public enum CompanyDepartment {
    TECH("개발"),
    HR("인사"),
    ACCOUNT("회계"),
    FINANCE("재무"),
    MANAGEMENT("총무관리"),
    SALES("영업"),
    MARKETING("마케팅"),
    CONTENTS("콘텐츠");


    private String name;

    CompanyDepartment(String name) {
        this.name = name;
    }
}
