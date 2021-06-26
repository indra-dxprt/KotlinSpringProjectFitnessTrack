package com.fitness.fitnesstracker.repository


import com.fitness.fitnesstracker.domain.UserInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Repository
interface UserRepository : JpaRepository<UserInfo, Long> {

    fun findByUsername(username: String?): Optional<UserInfo>

    @Query(value = "select u from  ftr_user_info u where u.email_id= :emailId", nativeQuery = true)
    fun findByEmailId(emailId: String): Optional<UserInfo>

    @Modifying
    @Transactional
    @Query(
        value = "update ftr_user_info u set u.password = :password where u.email_id = :userEmail",
        nativeQuery = true
    )
    fun updateuserPassword(@Param("password") password: String?, @Param("userEmail") userEmail: String?)


}