package com.ebf.test.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    @Value("${security.jwt.token.secret-key:secretABA9}")
    private String secretKey = "secretABA9";
    @Value("${security.jwt.token.expire-length:300000}")
    private long validityInMilliseconds = 300000; // 5min
    private long refreshTokensValidityInMilliseconds = 72000000; // 20h

    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, String role, boolean isRefreshToken) {
        Claims claims = Jwts.claims().setSubject(username);

        //TODO add client IP and user agent to claims later
        claims.put("roles", role);
        claims.put("type", isRefreshToken ? "R" : "A");

        Date now = new Date();
        Date validity = new Date(now.getTime() + (isRefreshToken ? refreshTokensValidityInMilliseconds : validityInMilliseconds));
        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) throws AuthenticationException {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                logger.debug("Access token is expired");
                return false;
            }
            return true;
        } catch (ExpiredJwtException e) {
            logger.debug("Access token is expired");
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthenticationException("Invalid Authentication token") {
                private static final long serialVersionUID = 6797292992141995377L;
            };
        }
    }

}