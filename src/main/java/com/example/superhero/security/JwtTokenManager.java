package com.example.superhero.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtTokenManager {

    // IMPORTANT: au moins 32 caractères pour HMAC-SHA
    private static final String SECRET = "CHANGE_ME_32_CHARS_MINIMUM_SECRET_KEY!!";
    private static final long EXP_MS = 1000L * 60 * 60 * 2; // 2 heures

    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    public static String generateToken(String subject) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXP_MS))
                .signWith(KEY)
                .compact();
    }

    public static String getSubject(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public static boolean isValid(String token) {
        try {
            getSubject(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
