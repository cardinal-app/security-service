package io.jrsmth.cardinal.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.jrsmth.cardinal.security.auth.registration.RegistrationData
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
class RegistrationIT extends Specification {

    @Autowired MockMvc mockMvc
    @Autowired ObjectMapper mapper
    @Autowired UserRepository userRepo

    def email = "ema.il@address.com"
    def endpoint = "/auth/register"

    def "should register user with correct response when valid registration data used"() {
        // TODO
    }

    def "should reject registration with correct response when duplicate email is used"() {
        given:
        def data = new RegistrationData()
        data.email = email

        def existingUser = new User(true, "", "", email, "")
        userRepo.save(existingUser)

        expect: 'datasource is seeded correctly for test'
        userRepo.findById(1L).get().email == email

        when:
        def result = mockMvc.perform(post(endpoint)
                .contentType("application/json;charset=UTF-8")
                .content(mapper.writeValueAsString(data)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn()

        then:
        result.getResponse().getContentAsString() == null

        and:
        noExceptionThrown()
    }

    // Note :: more tests around missing data?

}
