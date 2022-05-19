package com.handstandsam.convention.plugins

import com.handstandsam.ConventionPluginsGradlePropertyValues
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Kotlin Multiplatform Convention Plugin that is swappable with the JVM Plugin
 */
class KotlinMultiplatformConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val gradleProperties = ConventionPluginsGradlePropertyValues(target)

        // If property set with -Pmultiplatform
        if (gradleProperties.isMultiplatformEnabled) {
            println("Multiplatform Plugin Applied To: ${target.path}")
            target.plugins.apply("org.jetbrains.kotlin.multiplatform")
            SupportJvmSourceSetForMultiplatformPlugin(target).enable()
        } else {
            // Use Standard Kotlin Jvm Plugin
            target.plugins.apply(JvmLibConventionPlugin::class.java)
            SupportMultiplatformSourceSetForJvmPlugin(target).enable()
        }
    }
}
