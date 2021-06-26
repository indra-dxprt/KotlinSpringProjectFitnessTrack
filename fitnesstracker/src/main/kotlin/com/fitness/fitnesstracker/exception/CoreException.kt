package com.fitness.fitnesstracker.exception


class CoreException : RuntimeException {

    override var message: String
    var errorCode = 0
        private set

    constructor(errorCode: Int, message: String) : super(message) {
        this.errorCode = errorCode
        this.message = message
    }

    constructor(errorCode: Int, message: String, throwable: Throwable?) : super(message, throwable) {
        this.errorCode = errorCode
        this.message = message
    }

    constructor(message: String) : super(message) {
        this.message = message
    }


    companion object {
        private const val serialVersionUID = -5994053631370106273L
    }


}
