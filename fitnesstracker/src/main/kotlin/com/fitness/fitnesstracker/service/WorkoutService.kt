package com.fitness.fitnesstracker.service

import com.fitness.fitnesstracker.dto.StepCountMonthDto
import com.fitness.fitnesstracker.dto.StepCounterDayDto
import com.fitness.fitnesstracker.dto.StepCounterDto

interface WorkoutService {

    fun logStepForCurrentUser(stepCounterDto: StepCounterDto): Boolean
    fun aggregateStepsByMonth(month: String,year: Int): StepCountMonthDto
    fun aggregateStepsByYear(year: Int): Double?


    fun findTotalStepsInDaySeparatedByHours(date: String): List<StepCounterDayDto>
}