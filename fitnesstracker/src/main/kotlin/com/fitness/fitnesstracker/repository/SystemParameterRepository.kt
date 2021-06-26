package com.fitness.fitnesstracker.repository


import com.fitness.fitnesstracker.domain.SystemParameter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SystemParameterRepository : JpaRepository<SystemParameter?, Int?> {
    fun findByParamName(key: String): Optional<SystemParameter>
}