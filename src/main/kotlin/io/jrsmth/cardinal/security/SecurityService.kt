package io.jrsmth.cardinal.security

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SecurityService

	// Note :: on braces: https://stackoverflow.com/questions/59365267/intellij-idea-how-to-specify-main-class-in-kotlin
	fun main(args: Array<String>) {
		runApplication<SecurityService>(*args)
	}
