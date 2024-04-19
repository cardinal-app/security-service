package io.jrsmth.cardinal.security.token

import io.jrsmth.cardinal.common.util.resource.Messages
import io.jrsmth.cardinal.common.util.security.TokenManager
import org.springframework.stereotype.Service

@Service
class TokenService (
    val messages: Messages,
    val tokenManager: TokenManager,
){

    /** Determines if a token is valid */
    fun isValid(token: String): Boolean {
        return try {
            tokenManager.getClaimsFrom(token) != null

        } catch (e: Exception) {
            //log()
            false
        }
    }

}
