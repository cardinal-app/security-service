package io.jrsmth.cardinal.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthService

fun main(args: Array<String>) {
	runApplication<AuthService>(*args)
}
