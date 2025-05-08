// package org.econsult.healthcare.security;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Component;

// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// @Component
// public class JwtUtil {

//     @Value("${jwt.secret}")
//     private String secret;

//     public String generateToken(UserDetails userDetails) {
//         Map<String, Object> claims = new HashMap<>();
//         claims.put("role", userDetails.getAuthorities().stream().findFirst().get().getAuthority());
//         return createToken(claims, userDetails.getUsername());
//     }

//     private String createToken(Map<String, Object> claims, String subject) {
//         return Jwts.builder()
//             .setClaims(claims)
//             .setSubject(subject)
//             .setIssuedAt(new Date(System.currentTimeMillis()))
//             .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
//             .signWith(SignatureAlgorithm.HS256, secret).compact();
//     }

//     public String extractUsername(String token) {
//         return Jwts.parser().setSigningKey(secret)
//             .parseClaimsJws(token).getBody().getSubject();
//     }

//     public boolean validateToken(String token, UserDetails userDetails) {
//         final String username = extractUsername(token);
//         return (username.equals(userDetails.getUsername()));
//     }
// }

