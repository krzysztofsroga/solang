import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    val kotlinVersion: String by extra { "1.3.21" }

    repositories {
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
    }
}

plugins {
    kotlin("jvm") version "1.3.21"
	maven
}

apply {
    plugin("kotlinx-serialization")
}

group = "com.krzysztofsroga"
version = "0.1.2-alpha"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io") {
        name = "jitpack"
    }
    maven(url = "https://kotlin.bintray.com/kotlinx")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.kittinunf.fuel:fuel:2.0.1")
    implementation("com.github.kittinunf.fuel:fuel-coroutines:2.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.10.0")

    testImplementation("io.mockk:mockk:1.9.+")
    testImplementation("org.junit.jupiter:junit-jupiter:5.5+")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
