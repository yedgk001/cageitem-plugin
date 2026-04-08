plugins {
    id("groovy")
    id("com.gradleup.shadow") version "9.4.1"
}

group = "io.github.yedgk"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation("org.apache.groovy:groovy:5.0.0")
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

tasks {
    build {
        dependsOn("shadowJar")
    }
}