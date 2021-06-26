package com.fitness.fitnesstracker.util

import org.springframework.beans.BeansException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
class BeanUtil : ApplicationContextAware {
    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        appContext = applicationContext
    }

    companion object {
        @Autowired
        var appContext: ApplicationContext? = null
            private set
    }
}