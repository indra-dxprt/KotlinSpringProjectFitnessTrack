package com.fitness.fitnesstracker.server.helper

import com.fitness.fitnesstracker.domain.UserInfo
import com.fitness.fitnesstracker.dto.UserDto
import com.fitness.fitnesstracker.exception.CoreException
import com.fitness.fitnesstracker.repository.UserRepository
import com.fitness.fitnesstracker.util.WrapperObjectConvertor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.util.*


@Component
class CoreUserServiceHelper {

    @Autowired
    private val userRepository: UserRepository? = null

    fun isUserExists(userDto: UserDto) {
        // check duplicate user
        if (userDto.emailId == null) {
            throw CoreException(HttpStatus.BAD_REQUEST.value(), "User Email must be present.")
        } else if (userRepository!!.findByUsername(userDto.username).isPresent) {
            throw CoreException(HttpStatus.CONFLICT.value(), "User  already exists.")
        }
    }

    fun userDtoBuilder(userOp: Optional<UserInfo>, userDto: UserDto): UserDto {
        return WrapperObjectConvertor.userDtoBuilder(userOp, userDto)!!
    }
}
