package com.fitness.fitnesstracker.dto

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class RoleDto {
    @JsonProperty("id")
    private val roleId: Int? = null

    @JsonProperty("name")
    private val roleName: String? = null
}