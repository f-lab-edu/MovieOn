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
    implementation(project(":product-api:product-domain"))
    implementation(project(":common"))

    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter:${Version.fixtureMonkey}")
    // com.navercorp.fixturemonkey - org.glassfish:jakarta.el:3.0.3 보안 이슈로 버전 변경 (CVE-2021-28170)
    testImplementation("org.glassfish:jakarta.el:3.0.4")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
