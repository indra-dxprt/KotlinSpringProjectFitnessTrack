package com.fitness.fitnesstracker.server.response


data class ApiResponse(
    private var statusCode: Int? = null,
    private var isError: Boolean? = null,
    private var uri: String? = null,
    private var message: String? = null,

    //api specific response
    private var responseBody: Any? = null
) {

    fun getError() = isError

    fun getMessage() = message
    fun getStatusCode() = statusCode
    fun getUri() = uri
    fun getResponseBody() = responseBody

    override fun toString(): String {
        return "ApiResponse(statusCode=$statusCode, isError=$isError, uri='$uri', message='$message', responseBody='$responseBody')"
    }

    fun setUri(requestURI: String?) {
        this.uri = requestURI
    }

    fun setStatuscode(httpSucess: Int) {
        this.statusCode = httpSucess
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun setError(isError: Boolean) {
        this.isError = isError
    }

    fun setResponseBody(responseBody: Any) {
        this.responseBody = responseBody
    }


    //class  ApiResponse()


}