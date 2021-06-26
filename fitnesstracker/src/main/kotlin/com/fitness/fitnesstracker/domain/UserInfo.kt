package com.fitness.fitnesstracker.domain

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Where
import java.time.OffsetDateTime
import javax.persistence.*


@Entity
@Table(name = "ftr_user")
@Where(clause = "deleted=false")
data class UserInfo(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "incrementDomain", strategy = "increment")
    @Column(name = "user_id")
    private var userId: Long? = null,


    private var password: String? = null,

    private var email: String? = null,

    @Column(name = "last_name")
    private var lastName: String? = null,

    @Column(name = "first_name")
    private var firstName: String? = null,

    @Column(name = "created_at", nullable = false)
    private var createdAt: OffsetDateTime = OffsetDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    private var updatedAt: OffsetDateTime = OffsetDateTime.now(),

    private var deleted: Boolean? = null,

    @Column(name = "username")
    private var username: String? = null,

    private var active: Boolean? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "ftr_user_info_role",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    private var roles: MutableSet<Role> = mutableSetOf(),

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true, cascade = arrayOf(CascadeType.REMOVE))
    private var workouts: MutableSet<Workout> = mutableSetOf()
) {
    fun getPassword() = password
    fun getEmail() = email
    fun getRoles() = roles
    fun setRoles(roleList: MutableSet<Role>) {
        this.roles = roleList
    }

    fun getFirstName() = firstName
    fun getLastName() = lastName
    fun getUserName() = username
    fun getUserId() = userId
    fun setPassword(newpassword: String?) {
        this.password = newpassword
    }

    data class Builder(
        var firstName: String? = null,
        var lastName: String? = null,
        var username: String? = null,
        var password: String? = null,
        var email: String? = null
    ) {

        fun firstName(firstName: String) = apply { this.firstName = firstName }
        fun lastName(lastName: String) = apply { this.lastName = lastName }
        fun username(username: String) = apply { this.username = username }
        fun password(password: String) = apply { this.password = password }
        fun email(email: String) = apply { this.email = email }
        fun build() = UserInfo(
            null,
            password,
            email,
            lastName,
            firstName,
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            false,
            username,
            true
        )
    }
}
