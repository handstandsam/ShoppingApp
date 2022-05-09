package com.handstandsam

import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmLibConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val useMultiplatform = true
        if (useMultiplatform) {
            target.plugins.apply(JvmKmpLibConventionPlugin::class.java)
        } else {
            target.plugins.apply("org.jetbrains.kotlin.jvm")
            target.dependencies.add(
                "implementation",
                "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.20"
            )
        }
    }
}
