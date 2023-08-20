package ru.goncharenko.deck.infra

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class WebSecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf {
                disable()
            }
            cors { }
            authorizeRequests {
                authorize("/v3/api-docs/**", permitAll)
                authorize("/swagger-ui/**", permitAll)
                authorize("/swagger-ui.html", permitAll)
                authorize(anyRequest, authenticated)
            }
            oauth2ResourceServer {
                jwt { }
            }
        }
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(properties: CorsProperties): CorsConfigurationSource =
        UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", CorsConfiguration().apply {
                allowedHeaders = properties.allowedHeaders
                allowedOrigins = properties.allowedOrigins
                allowedMethods = properties.allowedMethods
            })
        }
}

@ConfigurationProperties(prefix = "cors")
data class CorsProperties(
    val allowedOrigins: List<String>,
    val allowedMethods: List<String>,
    val allowedHeaders: List<String>,
)