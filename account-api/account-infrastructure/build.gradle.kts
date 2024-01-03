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
    implementation(project(":account-api:account-domain"))
    implementation(project(":common"))
    implementation("io.jsonwebtoken:jjwt-api:${Version.jjwt}")
    implementation("io.jsonwebtoken:jjwt-impl:${Version.jjwt}")
    implementation("io.jsonwebtoken:jjwt-jackson:${Version.jjwt}")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}
