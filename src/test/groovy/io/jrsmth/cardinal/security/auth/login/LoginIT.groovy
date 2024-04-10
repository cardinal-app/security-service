package io.jrsmth.cardinal.security.auth.login

import com.fasterxml.jackson.databind.ObjectMapper
import io.jrsmth.cardinal.common.util.resource.Messages
import io.jrsmth.cardinal.common.util.security.TokenManager
import io.jrsmth.cardinal.security.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Integration tests for the Login Feature
 *
 * @author J. R. Smith
 * @since 8th April 2024
 */
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginIT extends Specification implements LoginTrait {

    @Autowired MockMvc mockMvc
    @Autowired ObjectMapper mapper
    @Autowired Messages messages
    @Autowired TokenManager tokenManager
    @Autowired UserRepository userRepo

    def endpoint = "/auth/login"

    def setup() {
        userRepo.deleteAll()
        userRepo.save(user)
    }

    def "should return jwt when user attempts to login with valid credentials"() {
        expect: 'user does exist in the database'
        userRepo.findByEmail(email as String) == Optional.of(user)

        when:
        def response = mockMvc.perform(post(endpoint)
                .contentType("application/json;charset=UTF-8")
                .content(mapper.writeValueAsString(data)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString()

        then:
        response instanceof String
        // TODO :: better check for valid JWT

        and:
        noExceptionThrown()
    }

    def "should reject login when credentials contain a user that does not exist"() {
        given:
        def unknownEmail = "unknown@email.com"
        def invalidData = getData(unknownEmail, password)

        expect: 'user does not exist in the database'
        userRepo.findByEmail(unknownEmail) == Optional.empty()

        when:
        def response = mockMvc.perform(post(endpoint)
                .contentType("application/json;charset=UTF-8")
                .content(mapper.writeValueAsString(invalidData)))
                .andExpect(status().isUnauthorized())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString()

        then:
        response == "Could not find your account"
        // FixMe :: messages.get("login.failure.no_user")

        and:
        noExceptionThrown()
    }

    def "should reject login when credentials contain an incorrect password"() {
        given:
        def incorrectPassword = 'incorrect-password'
        def invalidData = getData(email, incorrectPassword)

        expect: 'user does exist in the database'
        userRepo.findByEmail(email as String) == Optional.of(user)

        when:
        def response = mockMvc.perform(post(endpoint)
                .contentType("application/json;charset=UTF-8")
                .content(mapper.writeValueAsString(invalidData)))
                .andExpect(status().isUnauthorized())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString()

        then:
        response == "Incorrect password"
        // FixMe :: messages.get("login.failure.password")

        and:
        noExceptionThrown()
    }

    @Unroll
    def "should reject login when credentials are #invalid"() {
        when:
        def response = mockMvc.perform(post(endpoint)
                .contentType("application/json;charset=UTF-8")
                .content(mapper.writeValueAsString(rogue)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString()

        then:
        response == expected

        and:
        noExceptionThrown()

        where: // FixMe :: access to message bundle (expected)
        invalid             | rogue                   | expected
        "missing email"     | getData(null, lastName) | "Cannot login due to rogue data (email must be present) "
        "missing password"  | getData(email, null)    | "Cannot login due to rogue data (password must be present) "

    }

}
