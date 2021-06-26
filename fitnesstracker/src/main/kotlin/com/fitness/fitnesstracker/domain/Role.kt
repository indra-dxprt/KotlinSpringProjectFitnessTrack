package com.fitness.fitnesstracker.domain

import lombok.*
import java.time.OffsetDateTime
import javax.persistence.*

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "ftr_role")
class Role(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private val roleId: Int? = null,
    private val roleName: String? = null,

    @Column(name = "created_at", nullable = false)
    var createdAt: OffsetDateTime = OffsetDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: OffsetDateTime = OffsetDateTime.now(),

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private val users: Set<UserInfo>? = mutableSetOf()


) {
    fun getRoleId() = roleId
    fun getRoleName() = roleName


}