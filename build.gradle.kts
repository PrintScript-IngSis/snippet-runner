import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    id("org.jetbrains.kotlinx.kover") version "0.7.6"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
}

group = "printscript.group13"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
    mavenLocal()
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/printscript-ingsis/printscript")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("printscript:lexer:1.1.3")
    implementation("printscript:parser:1.1.3")
    implementation("printscript:common:1.1.3")
    implementation("printscript:formatter:1.1.3")
    implementation("printscript:linter:1.1.3")
    implementation("printscript:interpreter:1.1.3")
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.3.0")
    implementation("org.jetbrains.kotlinx:kover-gradle-plugin:0.7.6")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

koverReport {
    verify {
        rule {
            minBound(80)
        }
    }
}
