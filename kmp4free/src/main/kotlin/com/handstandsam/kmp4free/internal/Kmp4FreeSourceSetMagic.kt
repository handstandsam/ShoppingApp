package com.handstandsam.kmp4free.internal

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

/**
 * Allows us to use the SourceSet Structure of a Multiplatform Project in a JVM Project
 */
internal class Kmp4FreeSourceSetMagic(
    target: Project
) {
    private val configurations: ConfigurationContainer = target.configurations

    private val logger = target.logger

    private val kotlinProjectExtension: KotlinProjectExtension =
        target.extensions.getByType(KotlinProjectExtension::class.java)

    private val sourceSets: NamedDomainObjectContainer<KotlinSourceSet> =
        kotlinProjectExtension.sourceSets

    fun extendConfigurationsAndSourceSets(
        extendsFromSourceSetName: String,
        sourceSetName: String,
    ) {
        // Extend SourceSets
        logger.info("--------")
        sourceSets.findByName(sourceSetName)?.apply {
            logger.info("** SourceSets: $sourceSetName now includes sources from $extendsFromSourceSetName **")
            listOf(
                "src/$extendsFromSourceSetName/java",
                "src/$extendsFromSourceSetName/kotlin",
            ).forEach {
                kotlin.srcDir(it)
                logger.info("Added $it as a srcDir for $sourceSetName")
            }
            logger.info("--------")
        }

        // Extend Configurations
        logger.info("** Configurations: $sourceSetName extendsFrom $extendsFromSourceSetName **")
        listOf(
            "api",
            "implementation",
            "compileOnly",
            "runtimeOnly",
            "apiDependenciesMetadata",
            "implementationDependenciesMetadata",
            "compileOnlyDependenciesMetadata",
            "runtimeOnlyDependenciesMetadata",
        ).forEach {
            val configurationName = if (sourceSetName == "main") {
                it
            } else {
                "${sourceSetName}${it.capitalized()}"
            }
            val extendsFromSourceSetTargetConfigurationName =
                if (
//                    sourceSetName == "test" ||
                    extendsFromSourceSetName == "main") {
                    it
                } else {
                    "${extendsFromSourceSetName}${it.capitalized()}"
                }
            logger.info("$configurationName extendsFrom $extendsFromSourceSetTargetConfigurationName")
            configurations.maybeCreate(configurationName)
                .extendsFrom(
                    configurations.maybeCreate(
                        extendsFromSourceSetTargetConfigurationName
                    )
                )
        }
    }
}
