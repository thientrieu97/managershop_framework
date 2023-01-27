package com.security.jwt;

import com.management.SecurityMetersService;
import com.mapper.impl.UserMapper;
import com.model.request.DetailUser;
import com.security.SecurityUtils;
import com.service.UserInternalService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import tech.jhipster.config.JHipsterProperties;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String INVALID_JWT_TOKEN = "Invalid JWT token.";
    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);
    private final Key key;

    private final JwtParser jwtParser;

    private final long tokenValidityInMilliseconds;

    private final long tokenValidityInMillisecondsForRememberMe;

    private final UserInternalService userInternalService;

    private final SecurityMetersService securityMetersService;

    private final UserMapper userMapper;

    public TokenProvider(JHipsterProperties jHipsterProperties, UserInternalService userInternalService, SecurityMetersService securityMetersService, UserMapper userMapper) {
        this.userInternalService = userInternalService;
        this.userMapper = userMapper;
        byte[] keyBytes;
        String secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getBase64Secret();
        if (!ObjectUtils.isEmpty(secret)) {
            log.debug("Using a Base64-encoded JWT secret key");
            keyBytes = Decoders.BASE64.decode(secret);
        } else {
            log.warn(
                    "Warning: the JWT key used is not Base64-encoded. " +
                            "We recommend using the `jhipster.security.authentication.jwt.base64-secret` key for optimum security."
            );
            secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        }
        key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.tokenValidityInMilliseconds = 1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe =
                1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();

        this.securityMetersService = securityMetersService;
    }

    public String createToken(Authentication authentication, boolean rememberMe, Long timeExpire) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe)
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }
        DetailUser user = userInternalService.getUserByLogin(authentication.getName());
        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setId(user.getUserId())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
//        loginSessionManager.saveLoginSession(user.getUserId(), token, timeExpire);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public DetailUser getClaim(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return userMapper.mapToUser(claims);
    }

    public String getCurrentUserId() {
        String token = SecurityUtils.getCurrentUserJWT().orElseThrow(() -> new AccessDeniedException("Access Denied"));
        DetailUser userDetails = getClaim(token);
        return userDetails.getUserId();
    }

    public String getCurrentUserIdForDetails() {
        String currentUserId;
        try {
            String token = SecurityUtils.getCurrentUserJWT().orElse(null);
            currentUserId = getClaim(token).getUserId();
        } catch (Exception e) {
            return null;
        }
        return currentUserId;
    }

    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);

            return true;
        } catch (ExpiredJwtException e) {
            this.securityMetersService.trackTokenExpired();

            log.trace(INVALID_JWT_TOKEN, e);
        } catch (UnsupportedJwtException e) {
            this.securityMetersService.trackTokenUnsupported();

            log.trace(INVALID_JWT_TOKEN, e);
        } catch (MalformedJwtException e) {
            this.securityMetersService.trackTokenMalformed();

            log.trace(INVALID_JWT_TOKEN, e);
        } catch (SignatureException e) {
            this.securityMetersService.trackTokenInvalidSignature();

            log.trace(INVALID_JWT_TOKEN, e);
        } catch (
                IllegalArgumentException e) { // TODO: should we let it bubble (no catch), to avoid defensive programming and follow the fail-fast principle?
            log.error("Token validation error {}", e.getMessage());
        }
        return false;
    }

    /**
     * Find Token in buffer (Redis, Aerospike, ...), if don't have then generate a new token
     * if timeExpire is null, default expireTime will be used
     */
    public String findOrGenerateJwt(Authentication authentication, boolean rememberMe, Long timeExpire) {
//        DetailUser user = userInternalService.getUserByLogin(authentication.getName());
//        String token = loginSessionManager.findToken(user.getUserId());
        return  createToken(authentication, rememberMe, timeExpire);
    }
}
