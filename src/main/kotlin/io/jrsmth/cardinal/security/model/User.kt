package io.jrsmth.cardinal.security.model

import io.jrsmth.cardinal.common.model.AbstractEntity
import lombok.NoArgsConstructor

/**
 * Cardinal User
 * @since 26th March 2024
 */
@NoArgsConstructor
class User : AbstractEntity(
    var userId: Long,
    var createdDate: Date,
    var email: String,
    var password: String,
    var name: String?,
    var postcode: String?,
    var address: String?,
    var title: String?,
    var workPhone: String?,
    var mobilePhone: String?,
    var timeZone: String?,
    var termsConditionsAccepted: Boolean = false,
    var acceptedPrivacyPolicy: Boolean = false,
    var active: Boolean = false,
    var initiatorType: String?,
    var initiator: String?,
    var initiatorSponsor: String?) : Serializable {

    var storedToken: String? = null
    var otpDevice: OTPDevice? = null
    var businessName: String? = null
    var logo: ByteArray? = null
    var logoName: String? = null
    var adKeyword: String? = null
    var receiveEmails = false
    var admin = false
    var lawyer = false
    var allowDocxGeneration = false
    var passwordReset: String? = null
    var roles: List<String?>? = null

    companion object {
        const val POSTCODE_REGEX: String = "^[a-zA-Z]{1,2}[0-9Rr][0-9a-zA-Z]? ?[0-9][a-zA-Z]{2}$"
        const val PHONE_NO_REGEX: String = "^(0|(00|\\+)44 ?(\\(0\\))?)(\\d{9,10})$"

        @Serial val serialVersionUID = 4318686901428551840L
    }
}
