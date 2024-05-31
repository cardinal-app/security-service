import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("groovy")
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
	id("net.researchgate.release") version "3.0.2"
	id("maven-publish")
	kotlin("jvm") version "1.9.23"
	kotlin("plugin.spring") version "1.9.23"
}

group = "io.jrsmth.cardinal"
version = "0.0.0-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenLocal()
	mavenCentral()
	maven {
		name = "GitHubPackages"
		url = uri("https://maven.pkg.github.com/cardinal-app/common")
		credentials {
			username = System.getenv("GPR_USER")
			password = System.getenv("GPR_KEY")
		}
	}
}

dependencies {
	implementation("io.jrsmth.cardinal:common:0.0.2")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.apache.groovy:groovy-all:4.0.21")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	// Note :: https://stackoverflow.com/questions/33869606/intellij-15-springboot-devtools-livereload-not-working

	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("com.h2database:h2")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.spockframework:spock-core:2.4-M2-groovy-4.0")
	testImplementation("org.spockframework:spock-spring:2.4-M2-groovy-4.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootJar {
	enabled = false
}

tasks.jar {
	enabled = true
}

release {
	buildTasks.add("publish")
}

publishing {
	publications {
		create<MavenPublication>("gpr") {
			from(components["java"])
		}
	}

	repositories {
		maven {
			name = "GitHubPackages"
			url = uri("https://maven.pkg.github.com/cardinal-app/security-service")
			credentials {
				username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
				password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
			}
		}
	}
}

