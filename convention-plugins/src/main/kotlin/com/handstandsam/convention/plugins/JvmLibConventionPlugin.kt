package com.handstandsam.convention.plugins

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension

/**
 * Standard Kotlin JVM Plugin
 */
class JvmLibConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply("org.jetbrains.kotlin.jvm")
        setJvmVersionCompatibility(target)
    }

    companion object {
        fun setJvmVersionCompatibility(target: Project) {
            target.extensions.getByType(JavaPluginExtension::class.java).apply {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
        }
    }
}
