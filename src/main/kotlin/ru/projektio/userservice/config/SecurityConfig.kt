package ru.projektio.userservice.config

import ru.projektio.userservice.config.filters.JwtAuthFilter
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthFilter: JwtAuthFilter,
) {

    @Bean
    fun getEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Order(1)
    @Bean
    fun actuatorFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .securityMatcher("/actuator/**")
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.anyRequest().permitAll()
            }
            .cors { it.disable() }
            .csrf{ it.disable() }

        return http.build()
    }

    @Order(2)
    @Bean
    fun internalFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .securityMatcher("/internal/api/**")
            .cors { it.disable() }
            .csrf{ it.disable() }

        return http.build()
    }

    @Order(3)
    @Bean
    fun apiFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .securityMatcher("/**")
            .cors {  }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/users/me").authenticated()
                    .requestMatchers("/users/me/**").authenticated()
                    .requestMatchers("/users/**").hasAnyRole("ADMIN")
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}
