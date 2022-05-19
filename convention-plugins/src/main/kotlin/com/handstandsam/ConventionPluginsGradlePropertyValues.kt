package com.handstandsam

import org.gradle.api.GradleException
import org.gradle.api.Project

/**
 * Helpers to access relevant gradle properties in a clean way
 */
class ConventionPluginsGradlePropertyValues(private val project: Project) {
    val isMultiplatformEnabled: Boolean
        get() {
            return project.hasProperty("multiplatform")
        }

    val isJsEnabled: Boolean
        get() {
            if (!isMultiplatformEnabled) {
                throw GradleException("You cannot specify -Pjs without also specifying `-Pmultiplatform")
            }
            return project.hasProperty("js") && project.findProperty("js") != "false"
        }

    val isIosEnabled: Boolean
        get() {
            if (!isMultiplatformEnabled) {
                throw GradleException("You cannot specify -Pios without also specifying `-Pmultiplatform")
            }
            return project.hasProperty("ios") && project.findProperty("ios") != "false"
        }
}
