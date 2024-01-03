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
    implementation(project(":notification-api:notification-domain"))
    implementation(project(":common"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
