package io.jrsmth.cardinal.security.auth.registration

import io.jrsmth.cardinal.common.exception.CardinalException
import io.jrsmth.cardinal.common.util.Messages
import io.jrsmth.cardinal.security.user.User
import io.jrsmth.cardinal.security.user.UserRepository
import spock.lang.Specification
import spock.lang.Subject

/**
 * Unit tests for {@link RegistrationService}
 *
 * @author J. R. Smith
 * @since 4th April 2024
 */
class RegistrationServiceSpec extends Specification implements RegistrationTrait {

    def messages = Stub(Messages)
    def userRepo = Mock(UserRepository)

    @Subject
    def service = new RegistrationService(messages, userRepo)

    def "should create user when unique registration email is provided"() {
        given:
        userRepo.findByEmail(email) >> Optional.empty()

        when:
        service.register(data)

        then:
        1 * userRepo.save(_ as User) >> new User()
        noExceptionThrown()
    }

    def "should reject registration when provided email already exists"() {
        given:
        userRepo.findByEmail(email) >> Optional.of(new User())

        when:
        service.register(data)

        then:
        thrown(CardinalException)
        0 * userRepo.save(_ as User)
    }

}
