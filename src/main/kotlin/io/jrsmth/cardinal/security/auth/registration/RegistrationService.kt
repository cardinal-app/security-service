package io.jrsmth.cardinal.security.auth.registration

import io.jrsmth.cardinal.security.user.User
import io.jrsmth.cardinal.security.user.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RegistrationService(
    val userRepo: UserRepository
) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(RegistrationController::class.java)
    }

    fun register(data: RegistrationData): User {
        val user = User(true, data.firstName, data.lastName, data.email, data.password)

        if (isExisting(user)) {
            throw RegistrationException()

        } else {
            val registrant: User = userRepo.save(user)
            log.info("[register] User created with email [{}]!", registrant.email)

            return registrant
        }
    }

    private fun isExisting(user: User): Boolean {
        return false
    }

}
