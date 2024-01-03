import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.dsl.SpringBootExtension

buildscript {
    repositories {
        maven("https://plugins.gradle.org/m2/")
        mavenCentral()
    }

    dependencies {
        classpath("org.yaml:snakeyaml:1.28")
        classpath("org.jetbrains.kotlin:kotlin-allopen:1.8.20")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0-RC2")
    }
}

plugins {
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.8.20"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.8.20"
    jacoco
    idea

    kotlin("jvm") version "1.8.20"
    kotlin("plugin.spring") version "1.8.20"
    kotlin("plugin.jpa") version "1.8.20"
    kotlin("kapt") version "1.8.20"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = false
}

allprojects {
    repositories {
        maven("https://plugins.gradle.org/m2/")
        mavenCentral()
    }
}

subprojects {
    pluginManager.withPlugin("org.springframework.boot") {
        configure<SpringBootExtension> {
            mainClass.set("kr.flab.movieon.MovieOnApplication")
        }
    }

    apply {
        plugin("kotlin")
        plugin("io.spring.dependency-management")
        plugin("org.springframework.boot")
        plugin("kotlin-spring") // instead of "kotlin-allopen"
        plugin("kotlin-jpa")
        plugin("kotlin-kapt")
        plugin("kotlin-noarg")
        plugin("jacoco")
        plugin("idea")
    }

    noArg {
        annotation("com.kidsworld.core.annotation.NoArgsConstructor")
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-actuator")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("au.com.console:kassava:2.0.0")
        implementation("net.logstash.logback:logstash-logback-encoder:7.2")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.kotest:kotest-runner-junit5:5.5.4")
        testImplementation("io.kotest:kotest-assertions-core:5.5.4")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
        testImplementation("io.mockk:mockk:1.12.0")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    sourceSets {
        create("intTest") {
            compileClasspath += sourceSets.main.get().output + sourceSets.test.get().output
            runtimeClasspath += sourceSets.main.get().output + sourceSets.test.get().output

            resources.srcDir(file("src/intTest/resources"))
        }
    }

    tasks.withType<ProcessResources> {
        // 동일한 파일(main/resources/application.yaml, intTest/resources/application.yaml)이 있어서, 리소스 복사할 때 충돌 회피
        duplicatesStrategy = org.gradle.api.file.DuplicatesStrategy.INCLUDE
    }

    val intTestImplementation: Configuration by configurations.getting {
        extendsFrom(configurations.implementation.get(), configurations.testImplementation.get())
    }

    configurations["intTestImplementation"].extendsFrom(configurations.testImplementation.get())

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.test {
        extensions.configure(JacocoTaskExtension::class) {
            setDestinationFile(file("$buildDir/jacoco/jacoco.exec"))
        }

        reports {
            html.required.set(true)
            junitXml.required.set(true)
        }

        finalizedBy("integrationTest")
    }

    val integrationTest = task<Test>("integrationTest") {
        description = "Runs integration tests."
        group = "verification"

        testClassesDirs = sourceSets["intTest"].output.classesDirs
        classpath = sourceSets["intTest"].runtimeClasspath

        maxParallelForks = 1

        reports {
            html.required.set(true)
            junitXml.required.set(true)
        }

        extensions.configure(JacocoTaskExtension::class) {
            setDestinationFile(file("$buildDir/jacoco/jacoco-integration.exec"))
        }

        shouldRunAfter("test")
        finalizedBy("jacocoTestReport")
    }

    tasks.jacocoTestReport {
        reports {
            xml.required.set(true)
            xml.outputLocation.set(file("$buildDir/reports/tests/jacoco.xml"))
        }

        shouldRunAfter("integrationTest")
    }

    configure<JacocoPluginExtension> {
        toolVersion = "0.8.7"
    }

    tasks.withType<JacocoCoverageVerification> {
        violationRules {
            rule {
                element = "BUNDLE"

                limit {
                    counter = "BRANCH"
                    value = "COVEREDRATIO"
                    minimum = "0.0".toBigDecimal()
                }
            }
        }
    }
}

jacoco {
    toolVersion = "0.8.7"
}

task<JacocoReport>("jacocoRootReport") {
    dependsOn(subprojects.map { it.tasks.withType<JacocoReport>() })
    sourceDirectories.setFrom(
        subprojects.map {
            it.tasks.findByName("jacocoTestReport")!!.property("sourceDirectories")
        },
    )
    classDirectories.setFrom(subprojects.map { it.tasks.findByName("jacocoTestReport")!!.property("classDirectories") })
    executionData.setFrom(
        project.fileTree(".") {
            include("**/build/jacoco/**.exec")
        },
    )
    onlyIf {
        true
    }
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}
