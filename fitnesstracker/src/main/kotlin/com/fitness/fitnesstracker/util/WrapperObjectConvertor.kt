package com.fitness.fitnesstracker.util

import com.fitness.fitnesstracker.domain.UserInfo
import com.fitness.fitnesstracker.dto.UserDto
import java.util.*


class WrapperObjectConvertor {

    companion object {
        fun userEntityBuilder(userDto: UserDto): UserInfo {
            return userDto.emailId?.let {
                userDto.firstName?.let { it1 ->
                    userDto.password?.let { it2 ->
                        userDto.emailId?.let { it3 ->
                            userDto.lastName?.let { it4 ->
                                UserInfo.Builder().password(it2).username(it3)
                                    .firstName(it1).email(it3).lastName(it4).build()
                            }
                        }
                    }
                }
            }!!
        }

        fun userDtoBuilder(userOp: Optional<UserInfo>, userDto: UserDto): UserDto? {
            if (userOp.isPresent) {
                val user: UserInfo = userOp.get()
                return UserDto(
                    user.getUserId(),
                    user.getUserName(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName()
                )

            }
            return userDto
        }

    }
}