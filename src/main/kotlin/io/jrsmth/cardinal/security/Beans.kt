package io.jrsmth.cardinal.security

import io.jrsmth.cardinal.common.util.resource.Messages
import io.jrsmth.cardinal.common.util.security.TokenManager
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class Beans {

    @Bean
    fun messages(source: MessageSource): Messages = Messages(source)

    @Bean
    fun tokenManager(): TokenManager = TokenManager()

}
