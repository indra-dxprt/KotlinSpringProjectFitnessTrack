package com.fitness.fitnesstracker.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

class StepCountMonthDto(
    @JsonProperty("sum")
    private val totalSteps: Long,
    @JsonProperty("avg")
    private val averageSteps: Double
) : Serializable {
    fun getTotalSteps() = totalSteps
    fun getAverageSteps() = averageSteps
}