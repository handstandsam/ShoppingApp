package com.handstandsam.convention.plugins

import com.handstandsam.convention.plugins.Kmp4FreeLibConventionPlugin.Companion.setJvmVersionCompatibility
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Standard Kotlin JVM Plugin
 */
class JvmLibConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply("org.jetbrains.kotlin.jvm")
        setJvmVersionCompatibility(target)
    }
}
