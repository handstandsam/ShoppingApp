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
        val kmp4freeProperties = Kmp4FreePropertyValues(target)

        // If property set with -Pmultiplatform=true
        if (kmp4freeProperties.isMultiplatformEnabled) {
            println("Multiplatform Plugin Applied To: ${target.path}")
            target.plugins.apply("org.jetbrains.kotlin.multiplatform")
            Kmp4FreeMagic(target).enable()
        } else {
            // Use Standard Kotlin Jvm Plugin
            target.plugins.apply("org.jetbrains.kotlin.jvm")
            Kmp4FreeCloakingMagic(target).enable()
        }

        target.extensions.getByType(JavaPluginExtension::class.java).apply {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }
}
