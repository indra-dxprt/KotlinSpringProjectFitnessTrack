package com.fitness.fitnesstracker.config

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fitness.fitnesstracker.util.Constants
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.format.DateTimeFormatter

@Configuration
class Jackson2ObjectMapperConfig {

    @Bean
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder: Jackson2ObjectMapperBuilder ->
            builder.simpleDateFormat(Constants.UI_DATE_TIME_FORMAT)
            builder.serializers(LocalDateSerializer(DateTimeFormatter.ofPattern(Constants.UI_DATE_FORMAT)))
            builder.serializers(LocalDateTimeSerializer(DateTimeFormatter.ofPattern(Constants.UI_DATE_TIME_FORMAT)))
        }
    }
}