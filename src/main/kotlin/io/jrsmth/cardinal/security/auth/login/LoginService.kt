package io.jrsmth.cardinal.security.auth.login

import io.jrsmth.cardinal.common.exception.CardinalException
import io.jrsmth.cardinal.common.util.resource.Messages
import io.jrsmth.cardinal.common.util.security.TokenManager
import io.jrsmth.cardinal.security.user.User
import io.jrsmth.cardinal.security.user.UserRepository
import org.springframework.stereotype.Service

@Service
class LoginService (
    val messages: Messages,
    val tokenManager: TokenManager,
    val userRepo: UserRepository
){

    /** Authenticate user with token */
    @Throws(CardinalException::class)
    fun login(attempt: LoginData): String {
        val existing: User = userRepo.findByEmail(attempt.email!!)
            .orElseThrow {
                CardinalException(messages, LoginFailure.NO_USER.reason())
            }

        if (isIncorrectPassword(existing.password, attempt.password)) {
            throw CardinalException(messages, LoginFailure.PASSWORD.reason())
        }

        return tokenManager.generateFor(existing.id)
    }

    /** Determine if the password is incorrect */
    fun isIncorrectPassword(actual: String?, attempt: String?): Boolean {
        // TODO :: add hashing magic
        return !actual.equals(attempt)
    }

}
