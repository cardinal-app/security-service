package io.jrsmth.cardinal.security.auth.registration

import io.jrsmth.cardinal.security.user.User
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import jakarta.validation.Valid

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/register")
class RegistrationController(
    val service: RegistrationService
) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(RegistrationController::class.java)
    }

    /** Registers a new user */
    @PostMapping("/")
    @Throws(Exception::class) // Remove?
    fun register(
        request: HttpServletRequest, response: HttpServletResponse, @Valid @RequestBody user: RegistrationData
    ): ResponseEntity<Any> {
        return try {
            val registrant: User = service.register(user)
            ResponseEntity.ok(registrant)

        } catch (e: RegistrationException) {
            log.error("Registration failed with error [{}]", e.message)
            ResponseEntity.badRequest().body(e.message)
        }
    }

}
