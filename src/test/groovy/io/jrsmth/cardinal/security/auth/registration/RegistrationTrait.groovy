package io.jrsmth.cardinal.security.auth.registration

import spock.lang.Shared

trait RegistrationTrait {

    @Shared def firstName = "Ema"
    @Shared def lastName = "Il"
    @Shared def email = "ema.il@address.com"
    @Shared def password = "password"

    /** Return sample email as string */
    String getEmail() { email }

    /** Return sample registration data */
    RegistrationData getData() {
        return getData(firstName, lastName, email, password)
    }

    /** Return sample registration data */
    RegistrationData getData(firstName, lastName, email, password) {
        def data = new RegistrationData()

        data.firstName = firstName
        data.lastName = lastName
        data.email = email
        data.password = password

        return data
    }

}
