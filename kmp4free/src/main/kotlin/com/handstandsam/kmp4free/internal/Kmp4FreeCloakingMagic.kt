package com.handstandsam.kmp4free.internal

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

/**
 * Allows us to use the SourceSet Structure of a Multiplatform Project in a JVM Project
 */
class Kmp4FreeCloakingMagic(private val target: Project) {

    /**
     * Allow us to specify dependencies as "commonMain" or "commonTest"
     */
    fun enable() {
        target.extensions.getByType(KotlinProjectExtension::class.java).apply {
            sourceSets.getByName("main").apply {
                // Without this, would only have src/main/...
                kotlin.srcDirs("src/commonMain/java", "src/commonMain/kotlin")
            }

            Kmp4FreeSourceSetMagic(target).apply {
                extendSourceSet(
                    sourceSetName = "main",
                    extendsFromSourceSetName = "commonMain"
                )
                extendSourceSet(
                    sourceSetName = "test",
                    extendsFromSourceSetName = "commonTest"
                )
                extendSourceSet(
                    sourceSetName = "test",
                    extendsFromSourceSetName = "jvmTest"
                )
            }
        }
    }
}
