package io.jrsmth.cardinal.security.user

import io.jrsmth.cardinal.common.model.AbstractEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

/**
 * Cardinal User
 * @since 26th March 2024
 */
@Entity
@Table(name = "app_user")
data class User (
    var active: Boolean? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var password: String? = null
) : AbstractEntity()
