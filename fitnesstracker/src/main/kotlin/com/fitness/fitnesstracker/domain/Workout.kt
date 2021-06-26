package com.fitness.fitnesstracker.domain

import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "workout")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
class Workout(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "workout_id")
    private val id: Long? = null,

    @Column(name = "step")
    private val step: Long? = null,


    @Column(name = "created_at", nullable = false)
    private var createdAt: LocalDateTime? = null,

    @Column(name = "logged_at", nullable = false)
    private var loggedAt: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL),optional = false)
    @JoinColumn(name = "userId")
    private val user: UserInfo


) : Serializable