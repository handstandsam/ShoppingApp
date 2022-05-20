package com.handstandsam.kmp4free

import com.handstandsam.kmp4free.internal.Kmp4FreeMagic
import com.handstandsam.kmp4free.internal.Kmp4FreePropertyValues
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Kotlin Multiplatform Convention Plugin that is swappable with the JVM Plugin
 */
class Kmp4FreePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val kmp4FreeMagic = Kmp4FreeMagic(target)
        if (Kmp4FreePropertyValues(target).isMultiplatformEnabled) {
            // Use Kotlin Multiplatform Plugin
            println("Applying Plugin org.jetbrains.kotlin.multiplatform to ${target.path}")
            target.plugins.apply("org.jetbrains.kotlin.multiplatform")
            kmp4FreeMagic.enableKotlinMultiplatform()
        } else {
            // Use Kotlin JVM Plugin
            println("Applying Plugin org.jetbrains.kotlin.jvm to ${target.path}")
            target.plugins.apply("org.jetbrains.kotlin.jvm")
            kmp4FreeMagic.enableKotlinJvm()
        }
    }
}
