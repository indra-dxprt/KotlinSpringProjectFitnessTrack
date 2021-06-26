package com.fitness.fitnesstracker.dto

import java.io.Serializable

class StepCountAggregateMonthDto(
    private var month: String,
    private var year: Int,
) : Serializable {
    fun getYear() = year
    fun getMonth() = month
}