import org.gradle.kotlin.dsl.`kotlin-dsl`
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlin_version: String by extra
buildscript {
    var kotlin_version: String by extra
    kotlin_version = "1.2.51"
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlinModule("gradle-plugin", kotlin_version))
    }
}

plugins {
    `kotlin-dsl`
}
apply {
    plugin("kotlin")
}
dependencies {
    compile(kotlinModule("stdlib-jdk8", kotlin_version))
}
repositories {
    mavenCentral()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}