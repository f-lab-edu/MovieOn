plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

/**
 * 전체 프로젝트의 gradle plugin 버전을 지정 한다.
 */
object PluginVersion {
    const val KOTLIN = "1.8.20"
    const val SPRING_BOOT = "3.1.2"
    const val SPRING_DEPENDENCY_MANAGEMENT = "1.1.2"
    const val ASCIIDOCTOR = "3.3.2"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginVersion.KOTLIN}")
    implementation("org.jetbrains.kotlin:kotlin-allopen:${PluginVersion.KOTLIN}")
    implementation("org.jetbrains.kotlin:kotlin-noarg:${PluginVersion.KOTLIN}")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:${PluginVersion.SPRING_BOOT}")
    implementation("io.spring.gradle:dependency-management-plugin:${PluginVersion.SPRING_DEPENDENCY_MANAGEMENT}")
    implementation("org.asciidoctor:asciidoctor-gradle-jvm:${PluginVersion.ASCIIDOCTOR}")
}
