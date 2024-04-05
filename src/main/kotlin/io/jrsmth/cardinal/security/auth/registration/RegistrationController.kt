package io.jrsmth.cardinal.security.auth.registration

import io.jrsmth.cardinal.common.exception.CardinalException
import io.jrsmth.cardinal.common.util.Messages
import io.jrsmth.cardinal.common.util.Response
import io.jrsmth.cardinal.security.user.User
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.function.Consumer


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/register")
class RegistrationController(
    val messages: Messages,
    val service: RegistrationService
) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(RegistrationController::class.java)
    }

    /** Register a new user */
    @PostMapping
    fun register(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @Valid @RequestBody user: RegistrationData
    ): ResponseEntity<Any> {
        return try {
            val registrant: User = service.register(user)
            Response.created(registrant)

        } catch (e: CardinalException) {
            log.error("Registration failed with error [{}]", e.message)
            Response.badRequest(e.message)

        }
    }

    /** Handle exception when rogue registration data provided */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): String {
        var errors = ""
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            errors += String.format("(%s) ", error.defaultMessage)
        })

        return messages.get(RegistrationFailure.ROGUE_DATA.reason(), errors)
    }

}
