package io.jrsmth.cardinal.security.token

import io.jrsmth.cardinal.common.util.resource.Messages
import io.jrsmth.cardinal.common.util.rest.Response
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
class TokenController(
    val messages: Messages,
    val service: TokenService
) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(TokenController::class.java)
    }

    /** Validates an Authorisation Token */
    @GetMapping("/validate")
    fun validate(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Any> {
        return try {
            val authorisation = request.getHeader("Authorization")
            val token = authorisation.split(" ")[1]
            log.debug("[validate] Successfully retrieved token from auth header")

            Response.ok(service.isValid(token))

        } catch (e: Exception) {
            log.debug("[validate] Token validation failed: {}", e.message)
            Response.badRequest(messages.get(TokenFailure.INVALID_AUTH.reason()))

        }
    }

}
