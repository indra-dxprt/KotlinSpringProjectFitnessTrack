package com.fitness.fitnesstracker.dto


import com.fitness.fitnesstracker.domain.Role
import com.fitness.fitnesstracker.domain.UserInfo
import com.fitness.fitnesstracker.service.CoreUserService
import com.fitness.fitnesstracker.service.impl.CoreUserServiceImpl
import com.fitness.fitnesstracker.util.BeanUtil
import org.springframework.context.ApplicationContext
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import java.util.stream.Collectors

class UserSession(
    private val id: Long? = null,
    private val firstName: String? = null,
    private val lastName: String? = null,
    private val isEnabled: Boolean,
    @Transient
    private var authorities: Set<GrantedAuthority> = HashSet(),
    private val password: String? = null,
    private val roles: MutableSet<Role> = mutableSetOf(),
    private val email: String? = null,
    private val userName: String? = null
) : UserDetails, Serializable {
    override fun getUsername() = userName
    override fun isCredentialsNonExpired() = true
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    fun getRoleList() = roles
    fun getId() = id
    override fun isEnabled() = isEnabled
    override fun getPassword() = password
    override fun getAuthorities(): Set<GrantedAuthority> = authorities


    companion object {
        private const val serialVersionUID = 1461727242880407417L
        fun create(user: UserInfo): UserSession {
            val context: ApplicationContext = BeanUtil.appContext!!
            val userService: CoreUserService = context.getBean("coreUserServiceImpl", CoreUserServiceImpl::class.java)
            val authorities: Set<GrantedAuthority> = userService.getRoles(user).stream()
                .map { role -> SimpleGrantedAuthority(role.getRoleName()) }.collect(Collectors.toSet())
            return UserSession(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                true,
                authorities,
                user.getPassword(),
                user.getRoles(),
                user.getEmail(),
                user.getUserName()
            )
        }
    }
}