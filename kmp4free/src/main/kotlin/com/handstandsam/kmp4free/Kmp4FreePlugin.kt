package com.handstandsam.kmp4free

import com.handstandsam.kmp4free.internal.Kmp4FreeCloakingMagic
import com.handstandsam.kmp4free.internal.Kmp4FreeMagic
import com.handstandsam.kmp4free.internal.Kmp4FreePropertyValues
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension

/**
 * Kotlin Multiplatform Convention Plugin that is swappable with the JVM Plugin
 */
class Kmp4FreePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        if (Kmp4FreePropertyValues(target).isMultiplatformEnabled) {
            // Use Kotlin Multiplatform Plugin
            println("Applying Plugin org.jetbrains.kotlin.multiplatform to ${target.path}")
            target.plugins.apply("org.jetbrains.kotlin.multiplatform")
            Kmp4FreeMagic(target).enable()
        } else {
            // Use Kotlin JVM Plugin
            println("Applying Plugin org.jetbrains.kotlin.jvm to ${target.path}")
            target.plugins.apply("org.jetbrains.kotlin.jvm")
            Kmp4FreeCloakingMagic(target).enable()
        }
    }
}
