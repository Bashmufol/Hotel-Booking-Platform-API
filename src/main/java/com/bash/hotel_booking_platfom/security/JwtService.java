package com.bash.hotel_booking_platfom.security;

import com.bash.hotel_booking_platfom.service.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.expiration.time}")
    private long exprirationTime;
    @Value("${jwt.secret.key}")
    private String secretKey;

    // Extracts username (subject) from the JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extracts token expiration date
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Checks if the token is already expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validates that the token belongs to the user and is not expired
    public boolean isTokenValid(String token, UserPrincipal user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    // Generates a new access token for the given user
    public String generateAccessToken(UserPrincipal user){
        return generateToken(new HashMap<>(), user, exprirationTime);
    }

    // Extracts a specific claim using a resolver function
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claim = extractAllClaims(token);
        return claimsResolver.apply(claim);
    }

    // Builds and signs a JWT with user details and expiration time
    private String generateToken(Map<String, Object> extraClaims, UserPrincipal userPrincipal, long expirationTime   ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Parses the token and retrieves all claims
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Decodes the secret key and prepares it for signing/verification
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
