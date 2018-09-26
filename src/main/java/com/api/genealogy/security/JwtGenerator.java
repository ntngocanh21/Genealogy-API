package com.api.genealogy.security;

import com.api.genealogy.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {

    public String generate(UserEntity userEntity) {
        Claims claims = Jwts.claims()
                .setSubject(userEntity.getUsername());
        claims.put("userId", String.valueOf(userEntity.getId()));
        claims.put("role", userEntity.getRole());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "genealogy")
                .compact();
    }
}
