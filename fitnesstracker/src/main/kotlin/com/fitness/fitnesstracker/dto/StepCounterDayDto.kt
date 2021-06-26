package com.fitness.fitnesstracker.dto

import java.io.Serializable

class StepCounterDayDto(
    private val steps: Long? = null,
    private val hour: Int? = null
) : Serializable