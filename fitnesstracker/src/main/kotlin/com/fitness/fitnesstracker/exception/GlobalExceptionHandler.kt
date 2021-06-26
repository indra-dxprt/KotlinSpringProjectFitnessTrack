package com.fitness.fitnesstracker.exception

import com.fitness.fitnesstracker.server.response.ApiResponse
import com.fitness.fitnesstracker.util.CoreUtil
import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.multipart.MaxUploadSizeExceededException
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(value = [CoreException::class])
    fun coreException(exception: CoreException): ResponseEntity<ApiResponse> {
        logger.error(exception.message, exception)
        val response = ApiResponse()
        response.setError(true)
        response.setMessage(exception.message)
        response.setStatuscode(exception.errorCode)
        return ResponseEntity<ApiResponse>(response, HttpStatus.valueOf(exception.errorCode))
    }

    @ExceptionHandler(value = [MaxUploadSizeExceededException::class])
    fun fileSizeLimitExceededException(exception: MaxUploadSizeExceededException): ResponseEntity<ApiResponse> {
        logger.error(exception.message, exception)
        val response = ApiResponse()
        response.setError(true)
        response.setMessage("File size is exceeds (max size:5MB)")
        response.setStatuscode(HttpStatus.BAD_REQUEST.value())
        return ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [Exception::class])
    fun exception(exception: Exception): ResponseEntity<ApiResponse> {
        logger.error(exception.message, exception)
        val response = ApiResponse()
        response.setError(true)
        response.setMessage(exception.message)
        response.setStatuscode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        return ResponseEntity<ApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    /**
     * method to send error response from filter
     *
     * @param request
     * @param response
     * @param statusCode
     * @param message
     */
    fun sendErrorReponse(
        request: HttpServletRequest, response: HttpServletResponse, statusCode: Int,
        message: String?
    ) {
        val apiResponse = ApiResponse()
        apiResponse.setError(true)
        apiResponse.setUri(request.requestURI)
        apiResponse.setMessage(message)
        apiResponse.setStatuscode(statusCode)
        try {
            response.reset()
            response.status = statusCode
            response.contentType = "application/json"
            response.writer.write(CoreUtil.convertObjectToJson(apiResponse))
        } catch (e: IOException) {
            throw CoreException("IO Exception")
        }
    }

    companion object {
        private val logger = LogManager.getLogger(
            GlobalExceptionHandler::class.java
        )
    }
}