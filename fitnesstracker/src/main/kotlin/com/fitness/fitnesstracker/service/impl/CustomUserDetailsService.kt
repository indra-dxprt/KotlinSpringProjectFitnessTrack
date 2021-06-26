package com.fitness.fitnesstracker.service.impl

import com.fitness.fitnesstracker.domain.UserInfo
import com.fitness.fitnesstracker.dto.UserSession
import com.fitness.fitnesstracker.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService : UserDetailsService {
    @Autowired
    private val userRepository: UserRepository? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: UserInfo = userRepository!!.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("User not found with username: $username") }
        return UserSession.create(user)
    }
}