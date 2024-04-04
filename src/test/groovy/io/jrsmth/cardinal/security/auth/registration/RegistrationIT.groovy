package io.jrsmth.cardinal.security.auth.registration

import com.fasterxml.jackson.databind.ObjectMapper
import io.jrsmth.cardinal.common.util.Messages
import io.jrsmth.cardinal.security.user.User
import io.jrsmth.cardinal.security.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegistrationIT extends Specification implements RegistrationTrait {

    @Autowired MockMvc mockMvc
    @Autowired ObjectMapper mapper
    @Autowired Messages messages
    @Autowired UserRepository userRepo

    def endpoint = "/auth/register"

    def "should register user with correct response when valid registration data used"() {
        // TODO
    }

    def "should reject registration with correct response when duplicate email is used"() {
        given:
        def existingUser = new User(true, "", "", email, "")
        userRepo.save(existingUser)

        expect: 'datasource is seeded correctly for test'
        userRepo.findById(1L).get().email == email

        when:
        def response = mockMvc.perform(post(endpoint)
                .contentType("application/json;charset=UTF-8")
                .content(mapper.writeValueAsString(data)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString()

        then:
        response == "User already exists with this email address"
        // FixMe :: messages.get("registration.failure.user-exists")

        and:
        noExceptionThrown()
    }

    // Note :: more tests around missing data?

}
