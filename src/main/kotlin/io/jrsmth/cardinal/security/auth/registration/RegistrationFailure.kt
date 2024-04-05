package io.jrsmth.cardinal.security.auth.registration

import io.jrsmth.cardinal.common.exception.Reasonable

/**
 * Enhanced Enum for Reasonable Registration Failures
 *
 * @author J. R. Smith
 * @since 4th April 2024
 */
enum class RegistrationFailure : Reasonable {

    EMAIL_EXISTS,
    ROGUE_DATA;

    override fun reason(): String {
        return "registration.failure." + this.name.lowercase()
    }

}
