package io.jrsmth.cardinal.security.auth.login

import io.jrsmth.cardinal.security.user.User
import spock.lang.Shared

trait LoginTrait {

    @Shared String firstName = "Ema"
    @Shared String lastName = "Il"
    @Shared String email = "ema.il@address.com"
    @Shared String password = "password"

    /** Return sample login data */
    LoginData getData() {
        return getData(email, password)
    }

    /** Return sample login data */
    LoginData getData(email, password) {
        def data = new LoginData()

        data.email = email
        data.password = password

        return data
    }

    /** Return sample user data */
    User getUser() {
        def user = new User()

        user.active = true
        user.email = email
        user.firstName = firstName
        user.lastName = lastName
        user.password = password

        return user
    }

}
