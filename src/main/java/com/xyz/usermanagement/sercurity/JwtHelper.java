package com.xyz.usermanagement.sercurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {
    public static final long JWT_TOKEN_VALIDITY=5*60*60;  //token expiry
  //custom secret key
    private String secret = "ASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXC";

    public String getUserNameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims,T> claimResolver) {
        final Claims claims=getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    private Boolean isTokenExpired(String token){
        final Date expatriation= getExpirationDateFromToken(token);
        return expatriation.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token,Claims::getExpiration);
    }
    public String generateToken(UserDetails userDetails){
        Map<String, Object> clams=new HashMap<>();
        return doGenerateToken(clams,userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claim, String username) {
        return Jwts.builder()
                .setClaims(claim)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS512,secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userName=getUserNameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}