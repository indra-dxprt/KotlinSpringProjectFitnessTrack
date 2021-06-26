package com.fitness.fitnesstracker.domain

import lombok.*
import java.time.LocalDateTime
import javax.persistence.*

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "ftr_user_login")
class UserLogin(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null,
    private val clientIp: String? = null,
    private var loginTime: LocalDateTime? = null,
    private val logoutTime: LocalDateTime? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    private val user: UserInfo? = null,

    ) {
    @PrePersist
    fun beforeCreate() {
        loginTime = LocalDateTime.now()
    }

    data class Builder(
        var clientIp: String? = null,
        var loginTime: LocalDateTime? = null,
        var logoutTime: LocalDateTime? = null,
        var user: UserInfo? = null
    ) {

        fun clientIp(clientIp: String) = apply { this.clientIp = clientIp }
        fun loginTime(loginTime: LocalDateTime) = apply { this.loginTime = loginTime }
        fun logoutTime(logoutTime: LocalDateTime) = apply { this.logoutTime = logoutTime }
        fun user(user: UserInfo) = apply { this.user = user }

        fun build() = UserLogin(
            null,
            clientIp,
            loginTime,
            logoutTime,
            user
        )
    }
}