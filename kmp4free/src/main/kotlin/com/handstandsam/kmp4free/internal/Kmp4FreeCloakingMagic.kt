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
//            sourceSets.getByName("main").apply {
//                kotlin.srcDirs("src/commonMain/java", "src/commonMain/kotlin")
//            }
//
//            sourceSets.getByName("test").apply {
//                kotlin.srcDirs("src/commonTest/java", "src/commonTest/kotlin")
//            }

            Kmp4FreeSourceSetMagic(target).apply {
                extendConfigurationsAndSourceSets(
                    extendsFromSourceSetName = "commonMain",
                    sourceSetName = "main",
                )
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
}
