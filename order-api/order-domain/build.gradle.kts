tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = false
}

dependencies {
    implementation(project(":common"))
    implementation("jakarta.persistence:jakarta.persistence-api:${Version.jakartaPersistenceApi}")
    implementation("org.hibernate:hibernate-core:${Version.hibernateCore}")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}
