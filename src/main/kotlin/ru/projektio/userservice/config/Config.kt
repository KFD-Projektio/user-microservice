package ru.projektio.userservice.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import ru.projektio.userservice.config.properties.JwtProperties

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class Config