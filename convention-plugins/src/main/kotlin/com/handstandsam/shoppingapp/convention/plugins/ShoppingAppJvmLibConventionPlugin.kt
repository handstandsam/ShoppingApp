package com.handstandsam.shoppingapp.convention.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Standard Kotlin JVM Plugin
 */
class ShoppingAppJvmLibConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply("org.jetbrains.kotlin.jvm")
        ShoppingAppKmp4FreeLibConventionPlugin.setJvmVersionCompatibility(target)
    }
}
