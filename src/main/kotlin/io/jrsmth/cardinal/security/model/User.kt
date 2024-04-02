package io.jrsmth.cardinal.security.model

import io.jrsmth.cardinal.common.model.AbstractEntity

/**
 * Cardinal User
 * @since 26th March 2024
 */
data class User (
    var active: Boolean,
    var firstName: String?,
    var lastName: String?,
    var email: String?,
    var password: String?,
) : AbstractEntity()
