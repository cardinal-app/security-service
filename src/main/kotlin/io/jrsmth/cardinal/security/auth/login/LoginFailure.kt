package io.jrsmth.cardinal.security.auth.login

import io.jrsmth.cardinal.common.exception.Reasonable

/**
 * Enhanced Enum for Reasonable Login Failures
 *
 * @author J. R. Smith
 * @since 5th April 2024
 */
enum class LoginFailure : Reasonable {

    DISABLED,
    NO_USER,
    PASSWORD,
    ROGUE_DATA;

    override fun reason(): String {
        return "login.failure." + this.name.lowercase()
    }

}
