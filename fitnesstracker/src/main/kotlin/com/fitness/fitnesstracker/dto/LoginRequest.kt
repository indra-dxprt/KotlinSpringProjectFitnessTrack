package com.fitness.fitnesstracker.dto

import java.io.Serializable


class LoginRequest(

    private val username: String? = null,
    private val password: String? = null
) : Serializable {
    fun getUserName() = username
    fun getPassword() = password
}
