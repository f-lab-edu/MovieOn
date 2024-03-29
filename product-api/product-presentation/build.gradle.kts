plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = false
}

dependencies {
    implementation(project(":product-api:product-application"))
    implementation(project(":common"))
    implementation("org.springdoc:springdoc-openapi-ui:${Version.springdocOpenapi}")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter:${Version.fixtureMonkey}")
    // com.navercorp.fixturemonkey - org.glassfish:jakarta.el:3.0.3 보안 이슈로 버전 변경 (CVE-2021-28170)
    testImplementation("org.glassfish:jakarta.el:3.0.4")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
