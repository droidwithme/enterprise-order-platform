package com.ocean.userservice.infrastructure.config

import com.ocean.userservice.infrastructure.security.JwtProvider
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization")

        if (header != null && header.startsWith("Bearer ")) {
            val token = header.substring(7).trim()

            try {
                val auth = jwtProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = auth
            } catch (ex: ExpiredJwtException) {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                response.contentType = "application/json"
                response.writer.write(
                    """
                    {
                      "status":"FAIL",
                      "message":"Token expired",
                      "errors":["JWT token has expired"],
                      "data":null
                    }
                    """.trimIndent()
                )
                return
            } catch (ex: JwtException) {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                response.contentType = "application/json"
                response.writer.write(
                    """
                    {
                      "status":"FAIL",
                      "message":"Invalid token",
                      "errors":["JWT token is invalid"],
                      "data":null
                    }
                    """.trimIndent()
                )
                return
            }
        }

        filterChain.doFilter(request, response)
    }
}

