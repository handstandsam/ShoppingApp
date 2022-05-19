package com.handstandsam.kmp4free

import org.gradle.api.GradleException
import org.gradle.api.Project

/**
 * Helpers to access relevant gradle properties in a clean way
 */
internal class Kmp4FreePropertyValues(private val project: Project) {
    val isMultiplatformEnabled: Boolean
        get() {
            return project.findProperty(GRADLE_PROPERTY_MULTIPLATFORM) == "true"
        }

    val isJsEnabled: Boolean
        get() {
            if (!isMultiplatformEnabled) {
                throw GradleException("You cannot specify -Pjs without also specifying `-Pmultiplatform")
            }
            return project.findProperty(GRADLE_PROPERTY_JS) == "true"
        }

    val isIosEnabled: Boolean
        get() {
            if (!isMultiplatformEnabled) {
                throw GradleException("You cannot specify -Pios without also specifying `-Pmultiplatform")
            }
            return project.findProperty(GRADLE_PROPERTY_IOS) == "true"
        }

    companion object {
        const val GRADLE_PROPERTY_MULTIPLATFORM = "multiplatform"
        const val GRADLE_PROPERTY_JS = "js"
        const val GRADLE_PROPERTY_IOS = "ios"
    }
}
