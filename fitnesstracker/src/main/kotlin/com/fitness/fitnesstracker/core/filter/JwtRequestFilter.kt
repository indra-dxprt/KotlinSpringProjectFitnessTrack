package com.fitness.fitnesstracker.core.filter

import com.fitness.fitnesstracker.service.impl.CustomUserDetailsService
import com.fitness.fitnesstracker.util.JwtTokenUtil
import io.jsonwebtoken.ExpiredJwtException
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter : OncePerRequestFilter() {
    @Autowired
    lateinit var jwtUserDetailsService: CustomUserDetailsService

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil



    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val requestTokenHeader: String? = request.getHeader("Authorization")
        var username: String? = null
        var jwtToken: String = ""
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7)
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken)
            } catch (e: IllegalArgumentException) {
                jwtFilterLogger.log(Level.INFO, "Unable to get JWT Token")
            } catch (e: ExpiredJwtException) {
                jwtFilterLogger.log(Level.INFO, "JWT Token has expired")
            }
        } else {
            jwtFilterLogger.warn("JWT Token does not begin with Bearer String")
        }
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = jwtUserDetailsService.loadUserByUsername(username)
            if (jwtTokenUtil.validateToken(jwtToken, userDetails) ) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        chain.doFilter(request, response)
    }

    companion object {
        private val jwtFilterLogger = LogManager.getLogger(
            JwtRequestFilter::class.java
        )
    }
}