package com.fitness.fitnesstracker.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.*
import java.io.Serializable

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
class UserDto : Serializable {
    @JsonIgnore
    var id: Long? = null
    var firstName: String? = null
    var lastName: String? = null
    var emailId: String? = null

    @JsonIgnore
    var roleList: Set<RoleDto>? = null
    var password: String? = null

    @JsonIgnore
    var roles: String? = null


    var username: String? = null

    constructor()
    constructor(
        id: Long?,
        firstName: String?,
        lastName: String?,
        emailId: String?,
        roleList: Set<RoleDto>?,
        password: String?,
        roles: String?,
        username: String?
    ) {
        this.id = id
        this.firstName = firstName
        this.lastName = lastName
        this.emailId = emailId
        this.roleList = roleList
        this.password = password
        this.roles = roles
        this.username = username
    }

    constructor(userId: Long?, userName: String?, email: String?, firstName: String?, lastName: String?) {
        this.id = userId
        this.username = userName
        this.emailId = email
        this.firstName = firstName
        this.lastName = lastName
    }


    companion object {
        private const val serialVersionUID = 4343986717964774757L
    }
}