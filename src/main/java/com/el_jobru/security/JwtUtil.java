package com.el_jobru.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.el_jobru.models.user.User;
import io.github.cdimascio.dotenv.Dotenv;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    private final Algorithm algorithm;

    private final JWTVerifier verifier;

    private final String issuer;

    private static JwtUtil instance;

    private JwtUtil(String secret, String issuer) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.issuer = issuer;
        this.verifier = JWT.require(this.algorithm)
                .withIssuer(issuer)
                .build();
    }

    public static void initialize(Dotenv dotenv) {
        if (instance == null) {
            String secret = getEnv(dotenv, "JWT_SECRET");
            String issuer = getEnv(dotenv, "JWT_ISSUER");
            if (secret == null || secret.isBlank()) {
                throw new RuntimeException("Erro: JWT_SECRET não definido");
            }
            instance = new JwtUtil(secret, issuer);
        }
    }

    private static String getEnv(Dotenv dotenv, String key) {
        String value = System.getenv(key);
        if (value != null) {
            return value;
        }
        if (dotenv != null) {
            return dotenv.get(key);
        }
        return null;
    }

    public static JwtUtil getInstance() {
        if (instance == null) {
            throw new RuntimeException("JwtUtil não foi inicializado!");
        }
        return instance;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();
        Instant expire = now.plus(24, ChronoUnit.HOURS);

        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getId().toString())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expire))
                .withClaim("email", user.getEmail().value())
                .withClaim("role", user.getRole().toString())

                .sign(this.algorithm);
    }

    public DecodedJWT validateJwt(String token) throws JWTVerificationException {
        return this.verifier.verify(token);
    }

    public UUID getSubject(DecodedJWT token) {
        return UUID.fromString(token.getSubject());
    }

    public String getRole(DecodedJWT token) {
        return token.getClaim("role").asString();
    }
}
