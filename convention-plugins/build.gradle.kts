plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.android.gradle.plugin)
    compileOnly(gradleApi())
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(8)
}

gradlePlugin {
    plugins {
        plugins.create("com.handstandsam.jvm.lib") {
            id = "com.handstandsam.jvm.lib"
            implementationClass = "com.handstandsam.JvmLibConventionPlugin"
        }
        plugins.create("com.handstandsam.android.lib") {
            id = "com.handstandsam.android.lib"
            implementationClass = "com.handstandsam.AndroidLibConventionPlugin"
        }
    }
}
