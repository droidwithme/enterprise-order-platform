package com.ocean.userservice.infrastructure.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date
import java.util.UUID

@Component
class JwtProvider(
    @Value("\${security.jwt.secret}") private val secret: String,
    @Value("\${security.jwt.expiration-minutes}") private val expiryMinutes: Long
) {

    private val key: javax.crypto.SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())

    fun generateToken(userId: UUID, email: String): String {
        val now = Date()
        val expiry = Date(now.time + expiryMinutes * 60 * 1000)

        return Jwts.builder()
            .subject(userId.toString())
            .claim("email", email)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(key)   // HS256 auto
            .compact()
    }

    fun getClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun validate(token: String): Claims {
        return Jwts.parser()
            .verifyWith(key)          // instead of setSigningKey
            .build()
            .parseSignedClaims(token) // instead of parseClaimsJws
            .payload                  // instead of body
    }

    fun getAuthentication(token: String): Authentication {
        val claims = getClaims(token)
        val userId = claims.subject

        return UsernamePasswordAuthenticationToken(userId, null, emptyList())
    }


    fun getUserId(token: String): UUID {
        return UUID.fromString(getClaims(token).subject)
    }

    fun getEmail(token: String): String {
        return getClaims(token)["email"].toString()
    }
}
