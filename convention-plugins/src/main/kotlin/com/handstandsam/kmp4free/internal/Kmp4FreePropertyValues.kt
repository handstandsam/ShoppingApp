package com.handstandsam.kmp4free.internal

import org.gradle.api.GradleException
import org.gradle.api.Project

/**
 * Helpers to access relevant Gradle properties in a clean way
 */
internal class Kmp4FreePropertyValues(private val project: Project) {
    val isMultiplatformEnabled: Boolean
        get() {
            return project.findProperty(GRADLE_PROPERTY_MULTIPLATFORM) == "true"
        }

    val isJsEnabled: Boolean
        get() {
            if (!isMultiplatformEnabled) {
                throw GradleException("You cannot specify -P$GRADLE_PROPERTY_JS without also specifying `-P$GRADLE_PROPERTY_MULTIPLATFORM")
            }
            return project.findProperty(GRADLE_PROPERTY_JS) == "true"
        }

    val isIosEnabled: Boolean
        get() {
            if (!isMultiplatformEnabled) {
                throw GradleException("You cannot specify -P$GRADLE_PROPERTY_IOS without also specifying `-P$GRADLE_PROPERTY_MULTIPLATFORM")
            }
            return project.findProperty(GRADLE_PROPERTY_IOS) == "true"
        }

    companion object {
        const val GRADLE_PROPERTY_MULTIPLATFORM = "multiplatform"
        const val GRADLE_PROPERTY_JS = "js"
        const val GRADLE_PROPERTY_IOS = "ios"
    }
}
