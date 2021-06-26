package com.fitness.fitnesstracker.repository

import com.fitness.fitnesstracker.domain.Workout
import com.fitness.fitnesstracker.dto.StepCounterDayDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface WorkoutRepository : JpaRepository<Workout, Long> {

    @Query(
        value = "SELECT logged_at  FROM workout h WHERE  h.user_id=:userid ORDER BY logged_at DESC limit 1",
        nativeQuery = true
    )
    fun findLatestWorkOutByUserId(userid: Long): Optional<LocalDateTime>


    @Query(
        value = " select  avg(t.step) AS average from workout t where EXTRACT(MONTH FROM created_at )=:month and EXTRACT(YEAR FROM created_at)=:yearId and user_id=:userid group by date_part('month', t.created_at);",
        nativeQuery = true
    )
    fun findAverageCountAndTotalCountByMonthAndUserId(userid: Long, month: Int, yearId: Int): Double


    @Query(
        value = " select  sum(t.step)  AS total from workout t where EXTRACT(MONTH FROM created_at )=:month and EXTRACT(YEAR FROM created_at)=:yearId and user_id=:userid group by date_part('month', t.created_at);",
        nativeQuery = true
    )
    fun findTotalCountAndTotalCountByMonthAndUserId(userid: Long, month: Int, yearId: Int): Long

    @Query(
        value = " select avg(t.step) from   workout t where user_id =:userid and EXTRACT(YEAR FROM created_at )=:yearId   group by  date_part('year', t.created_at)",
        nativeQuery = true
    )
    fun findAverageStepCountByYear(userid: Long, yearId: Int): Double?


    @Query(
        value = " select  extract(hour from ARR_DATE) as hourday , sum(cast (step as numeric)) as total from workout where EXTRACT(MONTH FROM created_at )=:month and user_id =:userid and EXTRACT(YEAR FROM created_at )=:yearId and extract (day from created_at)=:day group by extract(hour from ARR_DATE) order by hourday desc",
        nativeQuery = true
    )
    fun findTotalStepCountByDay(userid: Long, yearId: Int, month: Int, day: Int): List<StepCounterDayDto>

    interface StepTotalInfo {
        val getAverage: String
        val getTotal: String
    }
}