package com.itainplace.springnblog.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.client-secret}")
    private String jwtSigningKey;
    @Value("${security.jwt.duration}")
    private Integer jwtDurationSeconds;


    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }


    public boolean validationToken(String token) {
        Jwts.parser()
                .setSigningKey(getSigningKey()) //Sets the signing key used to verify any discovered JWS digital signature.
                .build()                //Returns an immutable/thread-safe JwtParser created from the configuration from this JwtParserBuilder.
                .parseClaimsJws(token);
        return true;
    }

    public String getUsernameFromJWT(String token){
       Claims claims =  Jwts.parser()
                            .setSigningKey(getSigningKey()) //Sets the signing key used to verify any discovered JWS digital signature.
                            .build()                //Returns an immutable/thread-safe JwtParser created from the configuration from this JwtParserBuilder.
                            .parseClaimsJws(token)//Parses the specified compact serialized JWT string based on the builder's current configuration state and returns the resulting unsigned plaintext JWT instance.
                            .getBody();
       return claims.getSubject();
    }

    // 	getSubject() Returns the JWT sub (subject) value or null if not present.
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()
                                + Duration.ofSeconds(jwtDurationSeconds).toMillis()
                        )
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey()) //Sets the signing key used to verify any discovered JWS digital signature.
                .build()                //Returns an immutable/thread-safe JwtParser created from the configuration from this JwtParserBuilder.
                .parseClaimsJws(token)//Parses the specified compact serialized JWT string based on the builder's current configuration state and returns the resulting unsigned plaintext JWT instance.
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
