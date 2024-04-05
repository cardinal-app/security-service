package io.jrsmth.cardinal.security.auth.login

import io.jrsmth.cardinal.common.exception.CardinalException
import io.jrsmth.cardinal.common.util.Messages
import io.jrsmth.cardinal.security.auth.token.TokenService
import io.jrsmth.cardinal.security.user.User
import io.jrsmth.cardinal.security.user.UserRepository
import org.springframework.stereotype.Service

@Service
class LoginService (
    val messages: Messages,
    val tokenService: TokenService,
    val userRepo: UserRepository
){

    /** Authenticate user with token */
    @Throws(CardinalException::class)
    fun login(attempt: LoginData): String {
        val existing: User = userRepo.findByEmail(attempt.email!!).orElseThrow {
            CardinalException(messages, LoginFailure.NO_USER.reason())
        }

        if (isIncorrectPassword(existing.password, attempt.password)) {
            throw CardinalException(messages, LoginFailure.PASSWORD.reason())
        }

        return tokenService.generate(existing)
    }

    /** Determine if the password is incorrect */
    fun isIncorrectPassword(actual: String?, attempt: String?): Boolean {
        // TODO :: add hashing magic
        return !actual.equals(attempt)
    }

}
