package com.example.demo.security.jwt;

public class SecurityConstants {
    public static final String SECRET = "oursecretkeyoursecretkeyoursecretkey";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING_ACCESS_TOKEN = "access_token";
    public static final String HEADER_STRING_REFRESH_TOKEN = "refresh_token";

    public static final String SIGN_UP_URL = "/api/user/create";
}
