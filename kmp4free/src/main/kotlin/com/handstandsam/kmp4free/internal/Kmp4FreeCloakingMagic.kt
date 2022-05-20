package com.handstandsam.kmp4free.internal

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

/**
 * Allows us to use the SourceSet Structure of a Multiplatform Project in a JVM Project
 */
class Kmp4FreeCloakingMagic(private val target: Project) {

    /**
     * Allow us to specify dependencies as "commonMain", "commonTest", "jvmMain", "jvmTest"
     */
    fun enable() {
        Kmp4FreeSourceSetMagic(target).apply {
            // Main Sources
            extendConfigurationsAndSourceSets(
                extendsFromSourceSetName = "commonMain",
                sourceSetName = "main",
            )
            extendConfigurationsAndSourceSets(
                extendsFromSourceSetName = "jvmMain",
                sourceSetName = "main",
            )

            // Test Sources
            extendConfigurationsAndSourceSets(
                extendsFromSourceSetName = "commonTest",
                sourceSetName = "test",
            )
            extendConfigurationsAndSourceSets(
                extendsFromSourceSetName = "jvmTest",
                sourceSetName = "test",
            )
        }
    }
}
