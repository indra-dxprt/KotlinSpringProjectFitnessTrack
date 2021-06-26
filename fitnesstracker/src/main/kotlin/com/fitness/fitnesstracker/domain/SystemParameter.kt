package com.fitness.fitnesstracker.domain

import lombok.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "ftr_system_parameter")
@Getter
@Setter
@Data
class SystemParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val paramId: Int? = null

    private val paramName: String? = null
    private val paramValue: String? = null
    private val active = false
}