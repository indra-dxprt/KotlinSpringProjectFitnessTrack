package com.fitness.fitnesstracker.service


import com.fitness.fitnesstracker.domain.Role
import com.fitness.fitnesstracker.domain.UserInfo
import com.fitness.fitnesstracker.dto.UserDto
import javax.servlet.http.HttpServletRequest

interface CoreUserService {
    fun create(user: UserDto): UserDto
    fun getRoles(user: UserInfo): List<Role>
    fun logUserLogin(userName: String, request: HttpServletRequest)
    operator fun get(userId: Long): UserDto

    val currentUser: UserInfo?
}