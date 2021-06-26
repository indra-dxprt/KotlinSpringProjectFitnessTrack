package com.fitness.fitnesstracker.rest.controller

import com.fitness.fitnesstracker.config.Messages
import com.fitness.fitnesstracker.dto.UserDto
import com.fitness.fitnesstracker.server.response.ApiResponse
import com.fitness.fitnesstracker.service.CoreUserService
import com.fitness.fitnesstracker.util.CoreUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest


@RestController
class RegistrationController {
    @Autowired
    lateinit var coreUserService: CoreUserService

    @Autowired
    lateinit var messages: Messages

    @PostMapping("/register")
    fun registerUser(
        httpServletRequest: HttpServletRequest,
        @RequestBody userDto: UserDto
    ): ResponseEntity<ApiResponse> {
        var responseEntity: ResponseEntity<ApiResponse>
        val user = coreUserService.create(userDto)
        val response = ApiResponse()
        response.setUri(httpServletRequest.requestURI)
        response.setResponseBody(user)
        responseEntity = CoreUtil.buildApiResponse(
            user,
            httpServletRequest,
            messages.get("user.notregistered"),
            HttpStatus.OK.value(),
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.OK,
            HttpStatus.NOT_FOUND
        )
        return responseEntity
    }

}