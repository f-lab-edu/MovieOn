tasks.bootJar {
    enabled = true
}

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("plugin.jpa")
}

dependencies {
    implementation(project(":notification-api:notification-presentation"))
    implementation(project(":notification-api:notification-application"))
    implementation(project(":notification-api:notification-domain"))
    implementation(project(":notification-api:notification-infrastructure"))

    implementation(project(":account-api:account-presentation"))
    implementation(project(":account-api:account-application"))
    implementation(project(":account-api:account-domain"))
    implementation(project(":account-api:account-infrastructure"))

    implementation(project(":order-api:order-presentation"))
    implementation(project(":order-api:order-application"))
    implementation(project(":order-api:order-domain"))
    implementation(project(":order-api:order-infrastructure"))

    implementation(project(":payment-api:payment-presentation"))
    implementation(project(":payment-api:payment-application"))
    implementation(project(":payment-api:payment-domain"))
    implementation(project(":payment-api:payment-infrastructure"))

    implementation(project(":product-api:product-presentation"))
    implementation(project(":product-api:product-application"))
    implementation(project(":product-api:product-domain"))
    implementation(project(":product-api:product-infrastructure"))

    implementation(project(":query-api"))
    implementation(project(":common"))

    implementation("org.springdoc:springdoc-openapi-ui:${Version.springdocOpenapi}")
    implementation("org.springdoc:springdoc-openapi-security:${Version.springdocOpenapi}")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("it.ozimov:embedded-redis:${Version.embeddedRedis}") {
        exclude(group = "org.slf4j", module = "slf4j-simple")
    }
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.flywaydb:flyway-mysql")
    testImplementation("com.h2database:h2")
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter:${Version.fixtureMonkey}")
    // com.navercorp.fixturemonkey - org.glassfish:jakarta.el:3.0.3 보안 이슈로 버전 변경 (CVE-2021-28170)
    testImplementation("org.glassfish:jakarta.el:3.0.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
