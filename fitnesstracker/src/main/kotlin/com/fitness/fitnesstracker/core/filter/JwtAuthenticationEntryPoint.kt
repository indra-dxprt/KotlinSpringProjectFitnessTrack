package com.fitness.fitnesstracker.core.filter

import com.fitness.fitnesstracker.config.Messages
import com.fitness.fitnesstracker.exception.GlobalExceptionHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.Serializable
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint, Serializable {
    @Autowired
    lateinit var handler: GlobalExceptionHandler

    @Autowired
    private val messages: Messages? = null


    @Throws(IOException::class)
    override fun commence(
        request: HttpServletRequest, response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        handler.sendErrorReponse(request, response, HttpStatus.UNAUTHORIZED.value(), messages!!["401"])
    }

    companion object {
        private const val serialVersionUID = -7858869558953243875L
    }
}