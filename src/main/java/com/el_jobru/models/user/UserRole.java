package com.el_jobru.models.user;

import io.javalin.security.RouteRole;

public enum UserRole implements RouteRole {
    ANYONE,
    USER,
    ADMIN
}
