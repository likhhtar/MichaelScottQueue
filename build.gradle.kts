plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:lincheck:2.15")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
