package com.fitness.fitnesstracker.service

import com.fitness.fitnesstracker.dto.ChangePasswordDto


interface PasswordService {
    fun updatePassword(changePasswordDto: ChangePasswordDto?, isFirstTimeLogin: Boolean): Boolean
    fun forgotPassword(useremail: String): Boolean
}