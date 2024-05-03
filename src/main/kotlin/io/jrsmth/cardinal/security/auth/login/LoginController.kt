package io.jrsmth.cardinal.security.auth.login

import io.jrsmth.cardinal.common.exception.CardinalException
import io.jrsmth.cardinal.common.util.resource.Messages
import io.jrsmth.cardinal.common.util.rest.Response
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
@RequestMapping("/auth/login")
class LoginController(
    val messages: Messages,
    val service: LoginService
) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(LoginController::class.java)
    }

    /** Login user */
    @PostMapping
    fun login(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @Valid @RequestBody credentials: LoginData
    ): ResponseEntity<Any> {
        return try {
            val token: String = service.login(credentials)
            Response.ok(
                mapOf("token" to token)
            )

        } catch (e: CardinalException) {
            log.error("Login failed with error [{}]", e.message)
            Response.unauthorised(e.message)

        }
    }

    // Question :: make this a common utility? @CardinalValidation(failure=LoginFailure.ROGUE_DATA)
    /** Handle exception when rogue login data provided */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): String {
        var errors = ""
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            errors += String.format("(%s) ", error.defaultMessage)
        })

        return messages.get(LoginFailure.ROGUE_DATA.reason(), errors)
    }

}
