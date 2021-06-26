package com.fitness.fitnesstracker.rest.controller

import com.fitness.fitnesstracker.config.Messages
import com.fitness.fitnesstracker.dto.ChangePasswordDto
import com.fitness.fitnesstracker.server.response.ApiResponse
import com.fitness.fitnesstracker.service.PasswordService
import com.fitness.fitnesstracker.util.CoreUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/password")
@CrossOrigin
class PasswordController {

    @Autowired
    lateinit var messages: Messages

    @Autowired
    lateinit var passwordService: PasswordService


    @PostMapping("/changePassword")
    fun updatePassword(
        httpServletRequest: HttpServletRequest,
        @RequestBody changePasswordDto: ChangePasswordDto?
    ): ResponseEntity<ApiResponse> {
        val result: Boolean = passwordService.updatePassword(changePasswordDto, false)
        return CoreUtil.buildApiResponse(
            result, httpServletRequest,
            messages.get("oldpassword.notupdate"), HttpStatus.ACCEPTED.value(),
            HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.ACCEPTED, HttpStatus.NOT_ACCEPTABLE
        )
    }

    @PostMapping(value = ["/forget-password"])
    fun forgetPassword(
        httpServletRequest: HttpServletRequest,
        @RequestParam("useremail") useremail: String
    ): ResponseEntity<ApiResponse> {
        val result: Boolean = passwordService.forgotPassword(useremail)
        return CoreUtil.buildApiResponse(
            result, httpServletRequest, messages.get("oldpassword.notupdate"),
            HttpStatus.ACCEPTED.value(), HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.ACCEPTED,
            HttpStatus.NOT_ACCEPTABLE
        )
    }
}