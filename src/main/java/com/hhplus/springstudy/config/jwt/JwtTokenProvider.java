package com.hhplus.springstudy.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final Key key;
    private final long accessTokenExpTime;

    public JwtTokenProvider(@Value("${token.secret}") String secretKey, @Value("${token.expiration_time}") long expirationTime){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = expirationTime;
    }

    // 토큰 생성 메서드
    public String generateToken(String userId, String roles){
        return Jwts.builder()
                .setSubject(userId) // 사용자 정보 설정
                .claim("roles", roles)  // 사용자 권한 정보 추가
                .setIssuedAt(new Date())    // 토큰 생성 일자
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpTime))   // 토큰 만료 시간
                .signWith(key, SignatureAlgorithm.HS256)    // HMAC SHA256 서명
                .compact();
    }

    // 토큰 유효성 검증 메서드
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    // 사용자 아이디 추출
    public String extractUserId(String token){
        return parseClaims(token).getSubject();
    }

    // 역할 추출
    public String extractRoles(String token){
        return parseClaims(token).get("roles", String.class);
    }

    // 토큰에서 Claim 정보를 추출하는 메서드
    private Claims parseClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
