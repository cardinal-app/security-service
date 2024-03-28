package io.jrsmth.cardinal.security.auth.registration

import io.jrsmth.cardinal.security.model.User
import io.jrsmth.cardinal.security.model.data.RegistrationData
import io.jrsmth.cardinal.security.model.exception.RegistrationException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
class RegistrationController(
    val service: RegistrationService
) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(RegistrationController::class.java)
    }

    /** Registers a new user */
    @PostMapping("/")
    @Throws(Exception::class)
    fun register(
        request: HttpServletRequest, response: HttpServletResponse, @Valid @RequestBody user: RegistrationData
    ): RestResponse {
        return try {
            val registrant: User = this.service.register(user)
            RestResponse.successful(registrant)

        } catch (e: RegistrationException) {
            log.error("Registration failed with error [{}]", e.message)
            RestResponse.failed(e.message, 400, e.message)
        }
    }

}
