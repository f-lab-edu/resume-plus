package com.ddoddii.resume.model.eunm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    ROLE_ADMIN("ROLE_ADMIN", "관리자"),
    ROLE_USER("ROLE_USER", "사용자"),
    ROLE_GUEST("ROLE_GUEST", "게스트");

    private final String key;
    private final String title;
}
