package com.fitness.fitnesstracker.dto

import java.io.Serializable


data class ChangePasswordDto(
    private val oldPassword: String? = null,
    private val newPassword: String? = null,
    private val confirmNewPassword: String? = null
) : Serializable {
    fun getOldPassword() = oldPassword
    fun getNewPassword() = newPassword

    fun getConfirmNewPassword() = confirmNewPassword
}