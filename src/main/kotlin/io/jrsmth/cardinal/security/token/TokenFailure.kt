package io.jrsmth.cardinal.security.token

import io.jrsmth.cardinal.common.exception.Reasonable

/**
 * Enhanced Enum for Reasonable Token Failures
 *
 * @author J. R. Smith
 * @since 23rd April 2024
 */
enum class TokenFailure : Reasonable {

    INVALID_AUTH;

    override fun reason(): String {
        return "token.failure." + this.name.lowercase()
    }

}

