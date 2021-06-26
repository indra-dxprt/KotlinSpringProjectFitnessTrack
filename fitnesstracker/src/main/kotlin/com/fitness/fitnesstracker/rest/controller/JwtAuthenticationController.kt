package com.fitness.fitnesstracker.rest.controller

import com.fitness.fitnesstracker.config.Messages
import com.fitness.fitnesstracker.dto.LoginRequest
import com.fitness.fitnesstracker.dto.LoginResponse
import com.fitness.fitnesstracker.exception.CoreException
import com.fitness.fitnesstracker.service.CoreUserService
import com.fitness.fitnesstracker.service.impl.CustomUserDetailsService
import com.fitness.fitnesstracker.util.JwtTokenUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest


@RestController
@CrossOrigin
class JwtAuthenticationController {

    @Autowired
    private val authenticationManager: AuthenticationManager? = null

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    private val userDetailsService: CustomUserDetailsService? = null


    @Autowired
    private val coreUserService: CoreUserService? = null

    @Autowired
    lateinit var messages: Messages


    @RequestMapping(value = ["/authenticate"], method = [RequestMethod.POST])
    @Throws(Exception::class)
    fun createAuthenticationToken(
        @RequestBody authenticationRequest: LoginRequest, httpServletRequest: HttpServletRequest
    ): ResponseEntity<*>? {
        authenticationRequest.getPassword()?.let { authenticate(authenticationRequest.getUserName()!!, it) }

        val userDetails: UserDetails = userDetailsService!!.loadUserByUsername(authenticationRequest.getUserName()!!)
        val token:String = jwtTokenUtil.generateToken(userDetails)
        authenticationRequest.getUserName()?.let { coreUserService!!.logUserLogin(it, httpServletRequest) }
        return ResponseEntity.ok(LoginResponse(token))
    }

    @Throws(Exception::class)
    private fun authenticate(username: String, password: String) {
        try {
            authenticationManager
                ?.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
            throw CoreException(HttpStatus.UNAUTHORIZED.value(), "USER_DISABLED")
        } catch (e: BadCredentialsException) {
            throw CoreException(HttpStatus.UNAUTHORIZED.value(), "INVALID_CREDENTIALS")
        }
    }

}