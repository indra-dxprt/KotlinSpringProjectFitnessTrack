package com.fitness.fitnesstracker.service.impl


import com.fitness.fitnesstracker.domain.Role
import com.fitness.fitnesstracker.domain.UserInfo
import com.fitness.fitnesstracker.domain.UserLogin
import com.fitness.fitnesstracker.dto.UserDto
import com.fitness.fitnesstracker.repository.RoleRepository
import com.fitness.fitnesstracker.repository.UserLoginRepository
import com.fitness.fitnesstracker.repository.UserRepository
import com.fitness.fitnesstracker.server.helper.CoreUserServiceHelper
import com.fitness.fitnesstracker.service.CoreUserService
import com.fitness.fitnesstracker.util.CoreUtil
import com.fitness.fitnesstracker.util.RoleEnum
import com.fitness.fitnesstracker.util.WrapperObjectConvertor
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.servlet.http.HttpServletRequest


@Service("coreUserServiceImpl")
@DependsOn("encoder")
class CoreUserServiceImpl : CoreUserService {
    @Autowired
    private val userHelper: CoreUserServiceHelper? = null

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    private val userLoginRepository: UserLoginRepository? = null

    @Autowired
    private val roleRepository: RoleRepository? = null

    @Autowired
    lateinit var encoder: BCryptPasswordEncoder


    override fun create(user: UserDto): UserDto {
        val roleList: MutableSet<Role> = HashSet<Role>()
        userHelper!!.isUserExists(user)
        user.password= encoder.encode(user.password)
        var userRepo: UserInfo = WrapperObjectConvertor.userEntityBuilder(user)
        roleRepository?.findByRoleName(RoleEnum.User.description)?.let { roleList.add(it.get()) }
        userRepo.setRoles(roleList)
        userRepository.saveAndFlush(userRepo)
        return user
    }

    override fun getRoles(user: UserInfo): List<Role> {
        return ArrayList(user.getRoles())
    }

    override fun logUserLogin(userName: String, request: HttpServletRequest) {
        val userOp: Optional<UserInfo> = userRepository.findByUsername(userName)
        if (userOp.isPresent) {
            userLoginRepository!!.save(
                UserLogin.Builder().clientIp(getClientIpAddress(request)).user(userOp.get()).build()
            )
        }
    }


    private fun getClientIpAddress(request: HttpServletRequest?): String {
        for (header in IP_HEADER_CANDIDATES) {
            val ip = request!!.getHeader(header)
            if (ip != null && ip.length != 0 && !"unknown".equals(ip, ignoreCase = true)) {
                return ip
            }
        }
        return request!!.remoteAddr
    }


    @Transactional(readOnly = true)
    override fun get(userId: Long): UserDto {
        var userDto: UserDto? = null

        val userOp: Optional<UserInfo> = userRepository.findById(userId)
        userDto = userDto?.let { userHelper!!.userDtoBuilder(userOp, it) }
        return userDto!!
    }


    override val currentUser: UserInfo
        get() {
            val userName: String = CoreUtil.currentUserName
            val userOp: Optional<UserInfo> = userRepository.findByUsername(userName)
            return userOp.get()
        }


    companion object {
        private val logger = LogManager.getLogger(
            CoreUserServiceImpl::class.java
        )
        private val IP_HEADER_CANDIDATES = arrayOf(
            "X-Forwarded-For", "Proxy-Client-IP",
            "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR"
        )
    }

}