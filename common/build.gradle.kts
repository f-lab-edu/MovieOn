tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = false
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}
