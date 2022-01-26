package com.mini.jwt;

import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticatedUser {

    private String userId;
    private String role;

    public AuthenticatedUser(Claims claims) {
        this.userId = String.valueOf(claims.get("sub"));
        this.role = String.valueOf(claims.get("auth"));
    }
}
