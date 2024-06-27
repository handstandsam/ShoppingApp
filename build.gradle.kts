// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
//        maven { url = "https://maven.pkg.jetbrains.space/public/p/compose/dev" }
//        maven { url = "https://s01.oss.sonatype.org/content/repositories/snapshots/" }
    }
    dependencies {
        classpath(libs.android.gradle.plugin)
//        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.sqldelight.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath("com.handstandsam:convention-plugins")
//        classpath(libs.plugins.compose.compiler)
//        classpath("com.handstandsam.kmp4free:kmp4free:0.1.0")
//        classpath(libs.jetbrains.compose)
//        classpath(libs.plugins.jetbrains.compose) apply false
//        classpath(libs.plugins.compose.compiler) apply false

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

plugins {
    alias(libs.plugins.dependency.guard)
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false
}

dependencyGuard {
    configuration("classpath") {
        modules = false
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
//        maven { url = "https://maven.pkg.jetbrains.space/public/p/compose/dev" }
    }
}

// Allow Gradle Build Scans
gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}