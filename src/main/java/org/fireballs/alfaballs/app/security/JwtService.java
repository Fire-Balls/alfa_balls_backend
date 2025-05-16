package org.fireballs.alfaballs.app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
//    //todo перенести в конфиг
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access-token-expiration-ms}")
    private long accessTokenMs;

    public String generateAccessToken(String email) {
        return generateToken(email, new Date(System.currentTimeMillis() + accessTokenMs));
    }

    public String generateRefreshToken(String email, Date expiresAt) {
        return generateToken(email, expiresAt);
    }

    private String generateToken(String email, Date expiresAt) {
        Map<String, Object> claims = new HashMap<>();
        var jwt = Jwts.builder()
                .setClaims(claims)
                .setSubject(email) //Username is email
                .setIssuedAt(new Date())
                .setExpiration(expiresAt)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

        return jwt;
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractEmail(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

