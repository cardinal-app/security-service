package io.jrsmth.cardinal.security.auth.registration

import io.jrsmth.cardinal.security.model.User
import io.jrsmth.cardinal.security.model.registration.RegistrationData
import org.springframework.stereotype.Service

@Service
class RegistrationService {

    fun register(data: RegistrationData): User {
        return User(true, data.firstName, data.lastName, data.email, data.password)
    }

}
