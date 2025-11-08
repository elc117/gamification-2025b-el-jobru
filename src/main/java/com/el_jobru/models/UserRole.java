package com.el_jobru.models;

import io.javalin.security.RouteRole;

public enum UserRole implements RouteRole {
    ANYONE,
    USER,
    ADMIN
}
