package io.jrsmth.cardinal.security.auth.registration

import io.jrsmth.cardinal.common.exception.CardinalException
import io.jrsmth.cardinal.common.util.Messages
import io.jrsmth.cardinal.security.user.User
import io.jrsmth.cardinal.security.user.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class RegistrationService(
    val messages: Messages,
    val userRepo: UserRepository
) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(RegistrationService::class.java)
    }

    /** Register user if new to platform */
    @Throws(CardinalException::class)
    fun register(data: RegistrationData): User {
        val user = User(true, data.firstName, data.lastName, data.email, data.password)

        if (isExisting(user)) {
            log.warn("[register] User already exists with email [{}]!", data.email)
            throw CardinalException(messages, RegistrationFailure.EMAIL_EXISTS.reason())

        } else {
            val registrant: User = userRepo.save(user)
            log.info("[register] User created with email [{}]!", registrant.email)

            return registrant
        }
    }

    /** Determine if registration details are duplicate */
    private fun isExisting(user: User): Boolean {
        val existingUser: Optional<User> = userRepo.findByEmail(user.email!!)

        return existingUser.isPresent
    }

}
