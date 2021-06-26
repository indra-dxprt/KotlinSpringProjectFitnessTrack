package com.fitness.fitnesstracker.repository


import com.fitness.fitnesstracker.domain.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role, Int> {
    fun findByRoleName(roleName: String?): Optional<Role>
}