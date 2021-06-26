package com.fitness.fitnesstracker.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable


class LoginResponse(
    @JsonProperty("token")
   private val token: String
) : Serializable{
    fun getToken() = token

}

