package io.jrsmth.cardinal.security.auth.registration

trait RegistrationTrait {

    def email = "ema.il@address.com"

    String getEmail() {
        email
    }

    /** Return sample registration data */
    RegistrationData getData() {
        def data = new RegistrationData()

        data.firstName = "Ema"
        data.lastName = "Il"
        data.email = email
        data.password = "password"

        return data
    }

}
