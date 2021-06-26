package com.fitness.fitnesstracker.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable


data class StepCounterDto(
    @JsonProperty("step")
    private val steps: Long? = null,

    @JsonProperty("time")
    private val loggedTime: String?=null
) : Serializable {
    fun getSteps() = steps
    fun getLoggedTime() = loggedTime
}