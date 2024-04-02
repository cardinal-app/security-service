package io.jrsmth.cardinal.security.user

import io.jrsmth.cardinal.common.model.AbstractEntity
import jakarta.persistence.Table

/**
 * Cardinal User
 * @since 26th March 2024
 */
@Table(name = "user")
data class User (
    var active: Boolean,
    var firstName: String?,
    var lastName: String?,
    var email: String?,
    var password: String?,
) : AbstractEntity()
