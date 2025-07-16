package com.devspace.pagos360.paymentrequests

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import javax.sql.DataSource

@SpringBootApplication
class PayApplication

fun main(args: Array<String>) {
    runApplication<PayApplication>(*args)
}

@Configuration
class DataSourceConfig(
    @Value("\${spring.datasource.url}") val url: String,
    @Value("\${spring.datasource.username}") val username: String,
    @Value("\${spring.datasource.password}") val password: String,
) {

    @Bean
    fun dataSource(): DataSource =
        DataSourceBuilder.create()
            .url(url)
            .username(username)
            .password(password)
            .driverClassName("org.postgresql.Driver")
            .build()
}