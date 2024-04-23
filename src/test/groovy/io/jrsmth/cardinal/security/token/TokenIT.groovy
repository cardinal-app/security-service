package io.jrsmth.cardinal.security.token

import io.jrsmth.cardinal.common.util.security.TokenManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Integration tests for the token feature
 *
 * @author J. R. Smith
 * @since 24rd April 2024
 */
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TokenIT extends Specification {

    @Autowired MockMvc mockMvc
    @Autowired TokenManager manager

    def endpoint = "/token"
    def userId = 1L

    def "should verify a valid token"() {
        given:
        def token = manager.generateFor(userId)

        when:
        def response = mockMvc.perform(get("${endpoint}/validate")
                .header("Authorization", "Bearer ${token}"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString()

        then:
        response == true.toString()
        noExceptionThrown()
    }

    def "should identify token with an invalid signature"() {
        given:
        def invalidSignature = "invalid"
        def token = manager.generateFor(userId) + invalidSignature

        when:
        def response = mockMvc.perform(get("${endpoint}/validate")
                .header("Authorization", "Bearer ${token}"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString()

        then:
        response == false.toString()
        noExceptionThrown()
    }

    def "should identify validation request with invalid authorisation header"() {
        when:
        def response = mockMvc.perform(get("${endpoint}/validate")
                .header("Authorization", header))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString()

        then:
        response == "Authorisation header has invalid format" // TODO :: import msg.prop
        noExceptionThrown()

        where:
        header << ["Bearertoken", "Bearer", "token"]
    }

}
