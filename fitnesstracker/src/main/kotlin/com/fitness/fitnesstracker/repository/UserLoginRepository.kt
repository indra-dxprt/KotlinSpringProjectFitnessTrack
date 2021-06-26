package com.fitness.fitnesstracker.repository

import com.fitness.fitnesstracker.domain.UserLogin
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserLoginRepository : JpaRepository<UserLogin?, Long?>