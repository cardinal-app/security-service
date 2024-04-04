package io.jrsmth.cardinal.security.auth.registration

import io.jrsmth.cardinal.common.util.Messages
import io.jrsmth.cardinal.common.exception.Reasonable

/**
 * Enhanced Enum for Reasonable Registration Failures
 *
 * @author J. R. Smith
 * @since 4th April 2024
 */
enum class RegistrationFailure : Reasonable {

    EMAIL_EXISTS;

    override fun reason(messages: Messages): String {
        return messages.get("registration.failure." + this.name.lowercase())
    }

    override fun reason(messages: Messages, vararg params: String): String {
        return messages.get("registration.failure." + this.name.lowercase(), *params)
    }

}
