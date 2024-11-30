package com.duc82.finderapi.configs;

import com.duc82.finderapi.users.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${finder.jwt.secret}")
    private String SECRET;

    @Value("${finder.jwt.access_token.expiration}")
    private long ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${finder.jwt.refresh_token.expiration}")
    private long REFRESH_TOKEN_EXPIRATION_TIME;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
    }

    public String generateToken(User user) {
        return getToken(user, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    private String getToken(User user, long expirationTime) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().name());
        claims.put("fullName", user.getFullName());
        claims.put("email", user.getEmail());
        if (user.getProfile() != null) {
            claims.put("avatar", user.getProfile().getAvatar());
        }
        return createToken(claims, user.getEmail(), expirationTime);
    }

    public String generateRefreshToke(User user) {
        return getToken(user, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    private String createToken(Map<String, Object> claims, String subject, long expirationTime) {
        return Jwts.builder().claims(claims).subject(subject).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSecretKey(), Jwts.SIG.HS256).compact();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
