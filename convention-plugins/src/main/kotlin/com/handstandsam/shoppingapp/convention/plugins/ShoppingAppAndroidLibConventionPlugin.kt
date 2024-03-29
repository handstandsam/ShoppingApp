package com.handstandsam.shoppingapp.convention.plugins

import ANDROID_COMPILE_SDK_VERSION
import ANDROID_MIN_SDK_VERSION
import ANDROID_TARGET_SDK_VERSION
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class ShoppingAppAndroidLibConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply("com.android.library")
        target.plugins.apply("org.jetbrains.kotlin.android")
        target.extensions.getByType(LibraryExtension::class.java).also {
            it.compileSdk = target.ANDROID_COMPILE_SDK_VERSION
            it.compileOptions {
                targetCompatibility = JavaVersion.VERSION_17
                sourceCompatibility = JavaVersion.VERSION_17
            }
            it.defaultConfig {
                minSdk = target.ANDROID_MIN_SDK_VERSION
                targetSdk = target.ANDROID_TARGET_SDK_VERSION
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
        }
    }
}
