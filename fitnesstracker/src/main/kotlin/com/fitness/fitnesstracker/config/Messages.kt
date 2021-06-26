package com.fitness.fitnesstracker.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class Messages {
    @Autowired
    private val messageSource: MessageSource? = null
    private var accessor: MessageSourceAccessor? = null

    @PostConstruct
    private fun init() {
        accessor = MessageSourceAccessor(messageSource!!)
    }

    operator fun get(code: String?): String {
        return accessor!!.getMessage(code!!)
    }

    operator fun get(code: String?, vararg args: Any?): String {
        return accessor!!.getMessage(code!!, args)
    }
}