package com.fitness.fitnesstracker.config

import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig {
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate(clientHttpRequestFactory())
    }

    @Bean
    fun clientHttpRequestFactory(): HttpComponentsClientHttpRequestFactory {
        val clientHttpRequestFactory = HttpComponentsClientHttpRequestFactory()
        clientHttpRequestFactory.httpClient = httpClient()
        return clientHttpRequestFactory
    }

    @Bean
    fun httpClient(): CloseableHttpClient {
        return HttpClientBuilder.create().build()
    }
}