tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = false
}

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(":notification-api:notification-application"))
    implementation(project(":common"))
    implementation("org.springdoc:springdoc-openapi-ui:${Version.springdocOpenapi}")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
