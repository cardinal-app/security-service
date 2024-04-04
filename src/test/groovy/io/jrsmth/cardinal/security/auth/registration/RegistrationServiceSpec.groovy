package io.jrsmth.cardinal.security.auth.registration

import io.jrsmth.cardinal.security.user.User
import io.jrsmth.cardinal.security.user.UserRepository
import spock.lang.Specification
import spock.lang.Subject

class RegistrationServiceSpec extends Specification {

    def email = "ema.il@address.com"
    def userRepo = Mock(UserRepository)

    @Subject
    def service = new RegistrationService(userRepo)

    def "should create user when unique registration email is provided"() {
        given:
        def data = new RegistrationData()
        data.email = email

        userRepo.findByEmail(email) >> Optional.empty()

        when:
        service.register(data)

        then:
        1 * userRepo.save(_ as User) >> new User()
        noExceptionThrown()
    }

    def "should reject registration when provided email already exists"() {
        given:
        def data = new RegistrationData()
        data.email = email

        userRepo.findByEmail(email) >> Optional.of(new User())

        when:
        service.register(data)

        then:
        thrown(RegistrationException)
        0 * userRepo.save(_ as User)

    }

}
