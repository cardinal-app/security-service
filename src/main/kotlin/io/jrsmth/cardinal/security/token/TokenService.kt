package io.jrsmth.cardinal.security.token

import io.jrsmth.cardinal.common.util.resource.Messages
import io.jrsmth.cardinal.common.util.security.TokenManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TokenService (
    val messages: Messages,
    val tokenManager: TokenManager,
){

    companion object {
        val log: Logger = LoggerFactory.getLogger(TokenService::class.java)
    }

    /** Determines if a token is valid */
    fun isValid(token: String): Boolean {
        return try {
            tokenManager.getClaimsFrom(token) != null

        } catch (e: Exception) {
            log.debug("[isValid] Token validation failed: {}", e.message)
            false
        }
    }

}
