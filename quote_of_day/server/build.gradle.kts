import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    application
}

group = "me.palbp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.sparkjava:spark-core:2.9.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.+")
    implementation("org.slf4j:slf4j-simple:1.7.32")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.5.31")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}