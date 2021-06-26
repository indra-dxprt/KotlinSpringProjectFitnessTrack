package com.fitness.fitnesstracker.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fitness.fitnesstracker.dto.UserSession
import com.fitness.fitnesstracker.exception.CoreException
import com.fitness.fitnesstracker.server.response.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern
import javax.servlet.http.HttpServletRequest


class CoreUtil {

    companion object {
        val currentUserId: Long?
            get() {
                var id: Long? = null
                if (SecurityContextHolder.getContext() != null
                    && SecurityContextHolder.getContext().authentication != null
                ) {
                    val principal: Any = SecurityContextHolder.getContext().authentication.principal
                    if (principal is UserSession) {
                        id = (principal).getId()
                    }
                }
                return id
            }

        fun isCurrentUserRole(description: String?): Boolean {
            val currentUser: UserSession? = currentUser
            return if (currentUser != null) currentUser.getRoleList().stream()
                .anyMatch { item -> item.getRoleName().equals(description) } else false
        }

        val currentUser: UserSession
            get() {
                var userSession: UserSession? = null
                if (isNonEmpty(SecurityContextHolder.getContext().authentication)) {
                    val principal: Any = SecurityContextHolder.getContext().authentication.principal
                    if (principal is UserSession) {
                        userSession = principal
                    }
                }
                return userSession!!
            }

        fun isNonEmpty(collection: Collection<*>?): Boolean {
            return !(collection == null || collection.isEmpty())
        }

        fun isEmpty(collection: Collection<*>?): Boolean {
            return collection == null || collection.isEmpty()
        }

        fun isNonEmpty(str: String?): Boolean {
            return !(str == null || str.isEmpty())
        }

        fun isEmpty(str: String?): Boolean {
            return str == null || str.isEmpty()
        }

        fun isNonEmpty(`object`: Any?): Boolean {
            return `object` != null
        }

        fun isEmpty(`object`: Any?): Boolean {
            return `object` == null
        }

        fun isNull(`object`: Any?): Boolean {
            return `object` == null
        }

        fun isNonEmpty(map: Map<*, *>?): Boolean {
            return !(map == null || map.isEmpty())
        }

        fun buildApiResponse(
            obj: Any, req: HttpServletRequest,
            message: String, http_Sucess: Int, http_failure: Int, ok: HttpStatus, nOk: HttpStatus
        ): ResponseEntity<ApiResponse> {
            val responseEntity: ResponseEntity<ApiResponse>
            val response = ApiResponse()
            response.setUri(req.requestURI)
            var colllection: Collection<*>
            if (obj is Collection<*>) {
                colllection = obj
                if (isNonEmpty(colllection)) {
                    response.setStatuscode(http_Sucess)
                    responseEntity = ResponseEntity<ApiResponse>(response, ok)
                } else {
                    response.setStatuscode(http_failure)
                    response.setError(true)
                    response.setMessage(message)
                    responseEntity = ResponseEntity<ApiResponse>(response, nOk)
                }
            } else {
                if (isNonEmpty(obj)) {
                    response.setStatuscode(http_Sucess)
                    responseEntity = ok.let { ResponseEntity<ApiResponse>(response, it) }
                } else {
                    response.setStatuscode(http_failure)
                    response.setError(true)
                    response.setMessage(message)
                    responseEntity = nOk.let { ResponseEntity<ApiResponse>(response, it) }
                }
            }
            response.setResponseBody(obj)
            return responseEntity
        }

        fun convertObjectToJson(`object`: Any?): String? {
            return if (`object` == null) {
                null
            } else try {
                val mapper = ObjectMapper()
                val df: DateFormat = SimpleDateFormat(Constants.UI_DATE_FORMAT)
                mapper.dateFormat = df
                val javaTimeModule = JavaTimeModule()
                javaTimeModule.addSerializer<LocalDateTime>(
                    LocalDateTime::class.java,
                    LocalDateTimeSerializer(DateTimeFormatter.ofPattern(Constants.UI_DATE_TIME_FORMAT))
                )
                mapper.registerModule(javaTimeModule)
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                if (`object` is String) {
                    val str = `object`.toString().replace("’", "'")
                    return mapper.writeValueAsString(str)
                }
                mapper.writeValueAsString(`object`)
            } catch (e: JsonProcessingException) {
                throw CoreException("Object Parsing Exception")
            }
        }

        fun convertToLocalDateTime(date: Date): LocalDateTime? {
            return if (isNull(date)) {
                null
            } else date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        }

        fun convertToDate(localDateTime: LocalDateTime): Date? {
            return if (isNull(localDateTime)) {
                null
            } else Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
        }


        val currentUserName: String
            get() {
                val principal: Any = SecurityContextHolder.getContext().authentication.principal
                var userName: String
                userName = if (principal is UserDetails) {
                    (principal).username
                } else {
                    principal.toString()
                }
                return userName
            }

        fun <T> convertJsonStringToObject_1(jsonStr: String?, resultClass: Class<T>?): T? {
            var t: T
            val mapper = ObjectMapper()
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            val df: DateFormat = SimpleDateFormat(Constants.UI_DATE_FORMAT)
            mapper.dateFormat = df
            val javaTimeModule = JavaTimeModule()
            javaTimeModule.addDeserializer<LocalDateTime>(
                LocalDateTime::class.java, LocalDateTimeDeserializer(
                    DateTimeFormatter.ofPattern(Constants.INCOMING_UI_DATE_TIME_FORMAT)
                )
            )
            mapper.registerModule(javaTimeModule)
            t = try {
                mapper.readValue(jsonStr, resultClass)
            } catch (e: JsonProcessingException) {
                throw CoreException(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Json to Object conversion error", e
                )
            }
            return t
        }

        fun validateFileSize(filesize: Long) {
            if (filesize > 5242880) {
                throw CoreException(HttpStatus.CONFLICT.value(), "File size is exceeds (max size:5MB)")
            }
        }


        fun validateFileNameFormat(fileName: String?) {
            val regex = "^[a-zA-Z0-9]+$"
            val pattern = Pattern.compile(regex)
            if (pattern.matcher(fileName).matches()) {
                throw CoreException(
                    HttpStatus.CONFLICT.value(),
                    "Invalid File name Format, it should be Alphanumeric"
                )
            }
        }

        fun convertUtcToIstZone(localDateTime: LocalDateTime): LocalDate {
            val zonedUTCDate: ZonedDateTime = localDateTime.atZone(ZoneId.of("UTC"))
            val dateZoneIST: ZonedDateTime = zonedUTCDate.withZoneSameInstant(ZoneId.of("Asia/Kolkata"))
            val dateTime: LocalDateTime = dateZoneIST.toLocalDateTime()
            return dateTime.toLocalDate()
        }

    }
}