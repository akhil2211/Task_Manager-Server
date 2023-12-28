package com.example.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    GM_READ("gm:read"),
    GM_UPDATE("gm:update"),
    GM_CREATE("gm:create"),
    GM_DELETE("gm:delete"),

    PM_READ("pm:read"),
    PM_UPDATE("pm:update"),
    PM_CREATE("pm:create"),
    PM_DELETE("pm:delete")

;
    @Getter
    private  final String permission;

}
