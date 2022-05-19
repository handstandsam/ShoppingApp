package com.handstandsam.kmp4free

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

/**
 * Allows us to use the SourceSet Structure of a Multiplatform Project in a JVM Project
 */
class KmpCloakingMagic(private val target: Project) {

    /**
     * Allow us to specify dependencies as "commonMain" or "commonTest"
     */
    fun enable() {
        val configurations = target.configurations
        target.extensions.getByType(KotlinProjectExtension::class.java).apply {
            val main by sourceSets.getting {
                // Without this, would only have src/main/...
                kotlin.srcDirs("src/commonMain/java", "src/commonMain/kotlin")
            }

            // Make main extend from commonMain
            doMap(
                configurations = configurations,
                sourceSets = sourceSets,
                sourceSetName = "main",
                extendsFromSourceSetName = "commonMain",
            )

            doMap(
                configurations = configurations,
                sourceSets = sourceSets,
                sourceSetName = "test",
                extendsFromSourceSetName = "commonTest",
            )

            doMap(
                configurations = configurations,
                sourceSets = sourceSets,
                sourceSetName = "test",
                extendsFromSourceSetName = "jvmTest",
            )
        }
    }

    private fun String.isMainOrTest(): Boolean {
        return this == "main" || this == "test"
    }

    private fun doMap(
        configurations: ConfigurationContainer,
        sourceSets: NamedDomainObjectContainer<KotlinSourceSet>,
        sourceSetName: String,
        extendsFromSourceSetName: String,
    ) {
        println("** $sourceSetName extendsFrom $extendsFromSourceSetName **")
        val originalSourceSet = sourceSets.maybeCreate(sourceSetName)
        originalSourceSet.relatedConfigurationNames.forEach {
            val configurationName = if (originalSourceSet.name.isMainOrTest()) {
                it
            } else {
                "${originalSourceSet}${it.capitalized()}"
            }
            val extendsFromSourceSetTargetConfigurationName =
                if (extendsFromSourceSetName.isMainOrTest()) {
                    it
                } else {
                    "${extendsFromSourceSetName}${it.capitalized()}"
                }
            println("$configurationName extendsFrom $extendsFromSourceSetTargetConfigurationName")
            configurations.getByName(configurationName)
                .extendsFrom(configurations.maybeCreate(extendsFromSourceSetTargetConfigurationName))
        }
        println()
    }
}
