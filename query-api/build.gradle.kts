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
    implementation(project(":common"))
    implementation("org.springdoc:springdoc-openapi-ui:${Version.springdocOpenapi}")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:${Version.mybatisSpring}")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
