import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    val kotlinVersion: String by extra { "1.3.50" }

    repositories {
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
    }
}

plugins {
    kotlin("jvm") version "1.3.50"
	maven
}

apply {
    plugin("kotlinx-serialization")
}

group = "com.krzysztofsroga"
version = "0.1.4-alpha"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io") {
        name = "jitpack"
    }
    maven(url = "https://kotlin.bintray.com/kotlinx")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.kittinunf.fuel:fuel:2.2.+")
    implementation("com.github.kittinunf.fuel:fuel-coroutines:2.2.+")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.13.+")
    implementation("org.jsoup:jsoup:1.12.+")

    testImplementation("io.mockk:mockk:1.9.+")
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.+")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}
