package com.fitness.fitnesstracker.service.impl

import com.fitness.fitnesstracker.domain.UserInfo
import com.fitness.fitnesstracker.dto.ChangePasswordDto
import com.fitness.fitnesstracker.dto.UserSession
import com.fitness.fitnesstracker.exception.CoreException
import com.fitness.fitnesstracker.repository.UserRepository
import com.fitness.fitnesstracker.service.PasswordService
import com.fitness.fitnesstracker.util.CoreUtil
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*


@Service
class PasswordServiceImpl : PasswordService {

    private val logger: Logger = LogManager.getLogger(PasswordServiceImpl::class.java)

    @Autowired
    lateinit var passwordEncoder: BCryptPasswordEncoder

    @Autowired
    lateinit var userRepository: UserRepository


    override fun updatePassword(changePasswordDto: ChangePasswordDto?, isFirstTimeLogin: Boolean): Boolean {
        logger.info("Update Password Service is Getting Invoked")
        var result = false
        if (!changePasswordDto!!.getNewPassword().equals(changePasswordDto.getConfirmNewPassword())) {
            throw CoreException(HttpStatus.NOT_ACCEPTABLE.value(), "New Password and Confirm New Password are not same")
        }
        val userSession: UserSession = CoreUtil.currentUser
        val userEmail = userSession.username
        val checkPassword = passwordEncoder.matches(changePasswordDto.getOldPassword(), userSession.password)
        if (checkPassword) {
            val user: Optional<UserInfo> = userRepository.findByUsername(userEmail)
            if (user.isPresent) {
                user.get().setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()))
                userRepository.save(user.get())
                result = true
            }
        } else {
            throw CoreException(HttpStatus.NOT_ACCEPTABLE.value(), "Incorrect password")
        }
        return result
    }

    override fun forgotPassword(useremail: String): Boolean {
        logger.info("Forget Password Service is Getting Invoked for email id$useremail")
        var result = false
        val user: Optional<UserInfo> = userRepository.findByEmailId(useremail)
        if (user.isPresent) {
            val password: String = "Welcome@123"
            userRepository.updateuserPassword(passwordEncoder.encode(password), useremail)
            result = true
        }
        return result
    }
}