package io.jrsmth.cardinal.security.auth.token

import io.jrsmth.cardinal.security.user.User
import org.springframework.stereotype.Service

@Service
class TokenService {

    /** Generate a JWT token for given user */
    fun generate(user: User): String {
        return "jwt:${user.email}"
    }

}
