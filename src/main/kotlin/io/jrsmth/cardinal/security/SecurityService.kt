package io.jrsmth.cardinal.security

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SecurityService

fun main(args: Array<String>) {
	runApplication<SecurityService>(*args)
}
