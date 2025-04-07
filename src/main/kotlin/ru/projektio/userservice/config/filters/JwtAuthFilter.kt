package ru.projektio.userservice.config.filters
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.projektio.userservice.service.JwtTokenService

@Component
class JwtAuthFilter(
    private val jwtTokenService: JwtTokenService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader?.startsWith("Bearer ") == true) {
            val jwt = authHeader.substring(7)

            if (jwt.isNotBlank()) {
                try {
                    if (jwtTokenService.validateToken(jwt)) {
                        val username = jwtTokenService.getUsernameFromToken(jwt)
                        val authToken = UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            emptyList()
                        )
                        SecurityContextHolder.getContext().authentication = authToken
                    } else {
                        sendUnauthorizedError(response, "Invalid token")
                        return
                    }
                } catch (ex: Exception) {
                    sendUnauthorizedError(response, "Token verification failed")
                    return
                }
            } else {
                sendUnauthorizedError(response, "Empty token")
                return
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun sendUnauthorizedError(response: HttpServletResponse, message: String) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message)
    }
}
