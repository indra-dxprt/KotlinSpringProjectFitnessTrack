package com.fitness.fitnesstracker.service.impl

import com.fitness.fitnesstracker.domain.UserInfo
import com.fitness.fitnesstracker.domain.Workout
import com.fitness.fitnesstracker.dto.StepCountMonthDto
import com.fitness.fitnesstracker.dto.StepCounterDayDto
import com.fitness.fitnesstracker.dto.StepCounterDto
import com.fitness.fitnesstracker.exception.CoreException
import com.fitness.fitnesstracker.repository.UserRepository
import com.fitness.fitnesstracker.repository.WorkoutRepository
import com.fitness.fitnesstracker.service.WorkoutService
import com.fitness.fitnesstracker.util.CoreUtil
import org.apache.commons.lang.time.DateUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import java.util.concurrent.TimeUnit


@Service
class WorkoutServiceImpl : WorkoutService {

    @Autowired
    lateinit var workoutRepository: WorkoutRepository

    @Autowired
    lateinit var userRepository: UserRepository


    override fun logStepForCurrentUser(stepCounterDto: StepCounterDto): Boolean {
        val currentUserId: Long = CoreUtil.currentUserId!!
        val today: Date = DateUtils.truncate(Date(), Calendar.DAY_OF_MONTH)
        val df: DateFormat = SimpleDateFormat("HH:mm:ss")
        val time1 = LocalTime.parse(stepCounterDto.getLoggedTime()+":00")
        var startDate: LocalDate= LocalDate.now();
        val endTime: LocalDateTime = startDate.atTime(time1)

        if(workoutRepository.findLatestWorkOutByUserId(currentUserId).isPresent()) {
            val dur: Duration =
                Duration.between( workoutRepository.findLatestWorkOutByUserId(currentUserId).get(),LocalDateTime.now(),)

            val millis = dur.toMillis()
            val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(millis)
            if (minutes < 5) {
                throw CoreException(
                    HttpStatus.FORBIDDEN.value(),
                    "Please wait for 5 minutes from last entry to enter Step"
                )
            }
        }
        val userInfo : UserInfo = userRepository.findById(currentUserId).get()

        workoutRepository.save(Workout(
            null, stepCounterDto.getSteps(), endTime, LocalDateTime.now(),userInfo)
        )

        return true
    }

    override fun aggregateStepsByMonth(month: String, year: Int): StepCountMonthDto {
        val currentUserId: Long = CoreUtil.currentUserId!!
            val str = arrayOf(
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
            )
       val  monthIndex:Int = str.indexOf(month)
        val averageStep: Double = workoutRepository.findAverageCountAndTotalCountByMonthAndUserId(
            currentUserId,
            monthIndex + 1,year)

        val totalStep: Long = workoutRepository.findTotalCountAndTotalCountByMonthAndUserId(
            currentUserId,
            monthIndex + 1,year)
        return StepCountMonthDto(totalStep,averageStep)
    }

    override fun aggregateStepsByYear(year: Int): Double? {
        return workoutRepository.findAverageStepCountByYear(CoreUtil.currentUserId!!, year)

    }

    override fun findTotalStepsInDaySeparatedByHours(date: String): List<StepCounterDayDto> {
        val currentDate = LocalDate.parse(date)
        return workoutRepository.findTotalStepCountByDay(
            CoreUtil.currentUserId!!,
            currentDate.year,
            currentDate.monthValue,
            currentDate.dayOfYear
        )

    }
}