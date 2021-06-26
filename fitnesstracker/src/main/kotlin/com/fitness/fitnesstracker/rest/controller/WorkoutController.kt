package com.fitness.fitnesstracker.rest.controller

import com.fitness.fitnesstracker.config.Messages
import com.fitness.fitnesstracker.dto.StepCountMonthDto
import com.fitness.fitnesstracker.dto.StepCounterDayDto
import com.fitness.fitnesstracker.dto.StepCounterDto
import com.fitness.fitnesstracker.server.response.ApiResponse
import com.fitness.fitnesstracker.service.WorkoutService
import com.fitness.fitnesstracker.util.CoreUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/workout")
@CrossOrigin
class WorkoutController {

    @Autowired
    lateinit var messages: Messages

    @Autowired
    lateinit var workoutService: WorkoutService

    @PostMapping("/workout")
    fun logSteps(
        httpServletRequest: HttpServletRequest,
        @RequestBody stepCounterDto: StepCounterDto
    ): ResponseEntity<ApiResponse> {
        var result = workoutService.logStepForCurrentUser(stepCounterDto)
        return CoreUtil.buildApiResponse(
            result, httpServletRequest,
            messages.get("step.notupdated"), HttpStatus.ACCEPTED.value(),
            HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.ACCEPTED, HttpStatus.NOT_ACCEPTABLE
        )
    }

    @GetMapping("/getAggregateStepsByMonth")
    fun logSteps(
        httpServletRequest: HttpServletRequest,
        @RequestParam(name = "month") month:String, @RequestParam(name = "year") year:Int
    ): ResponseEntity<ApiResponse> {
        var result: StepCountMonthDto = workoutService.aggregateStepsByMonth(month,year)!!
        return CoreUtil.buildApiResponse(
            result, httpServletRequest,
            messages.get("step.notupdated"), HttpStatus.ACCEPTED.value(),
            HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.ACCEPTED, HttpStatus.NOT_ACCEPTABLE
        )
    }

    @GetMapping("/getAggregateStepsByYear/{year}")
    fun getAggregateStepsByYear(
        httpServletRequest: HttpServletRequest,
        @PathVariable("year") year: Int
    ): ResponseEntity<ApiResponse> {
        var result: Double = workoutService.aggregateStepsByYear(year)!!
        return CoreUtil.buildApiResponse(
            result, httpServletRequest,
            messages.get("step.notupdated"), HttpStatus.ACCEPTED.value(),
            HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.ACCEPTED, HttpStatus.NOT_ACCEPTABLE
        )
    }

    @GetMapping("/getAggregateStepsByDay/{day}")
    fun getAggregateStepsByDay(
        httpServletRequest: HttpServletRequest,
        @PathVariable("day") day: String
    ): ResponseEntity<ApiResponse> {
        var result: List<StepCounterDayDto> = workoutService.findTotalStepsInDaySeparatedByHours(day)
        return CoreUtil.buildApiResponse(
            result, httpServletRequest,
            messages.get("step.notupdated"), HttpStatus.ACCEPTED.value(),
            HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.ACCEPTED, HttpStatus.NOT_ACCEPTABLE
        )
    }
}


