package com.miniblog.back.auth.util;

public class TokenUtils {
    private TokenUtils() {
    }

    public static String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "").trim();
        }
        throw new IllegalArgumentException("Invalid Authorization header format.");
    }

}
