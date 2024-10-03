plugins {
	java
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	id("io.freefair.lombok") version "8.6"
}

group = "hexlet.code"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-devtools")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(platform("org.junit:junit-bom:5.10.0"))
	testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	runtimeOnly("com.h2database:h2")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
