package com.handstandsam

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

/**
 * Configures the [sourceSets][org.gradle.api.NamedDomainObjectContainer<org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet>] extension.
 */
internal
fun KotlinMultiplatformExtension.sourceSets(configure: Action<NamedDomainObjectContainer<KotlinSourceSet>>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("sourceSets", configure)

private val Project.kotlin: KotlinMultiplatformExtension get() = extensions.getByName("kotlin") as KotlinMultiplatformExtension

class JvmKmpLibConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply("org.jetbrains.kotlin.multiplatform")
        val configurations = target.configurations
        target.kotlin.apply {
            jvm {}
            sourceSets {
                val commonMain by getting {
                    kotlin.srcDirs("src/main/java", "src/main/kotlin")
                }

                val commonTest by getting {
                    kotlin.srcDirs("src/test/java", "src/test/kotlin")
                }

                val api by configurations.creating
                val implementation by configurations.creating
                val compileOnly by configurations.creating
                val runtimeOnly by configurations.creating
                val apiDependenciesMetadata by configurations.creating
                val implementationDependenciesMetadata by configurations.creating
                val compileOnlyDependenciesMetadata by configurations.creating
                val runtimeOnlyDependenciesMetadata by configurations.creating

                configurations.getByName(commonMain.apiConfigurationName)
                    .extendsFrom(api)
                configurations.getByName(commonMain.implementationConfigurationName)
                    .extendsFrom(implementation)
                configurations.getByName(commonMain.compileOnlyConfigurationName)
                    .extendsFrom(compileOnly)
                configurations.getByName(commonMain.runtimeOnlyConfigurationName)
                    .extendsFrom(runtimeOnly)
                configurations.getByName(commonMain.apiMetadataConfigurationName)
                    .extendsFrom(apiDependenciesMetadata)
                configurations.getByName(commonMain.implementationMetadataConfigurationName)
                    .extendsFrom(implementationDependenciesMetadata)
                configurations.getByName(commonMain.compileOnlyMetadataConfigurationName)
                    .extendsFrom(compileOnlyDependenciesMetadata)
                configurations.getByName(commonMain.runtimeOnlyMetadataConfigurationName)
                    .extendsFrom(runtimeOnlyDependenciesMetadata)

                val testApi by configurations.creating
                val testImplementation by configurations.creating
                val testCompileOnly by configurations.creating
                val testRuntimeOnly by configurations.creating
                val testApiDependenciesMetadata by configurations.creating
                val testImplementationDependenciesMetadata by configurations.creating
                val testCompileOnlyDependenciesMetadata by configurations.creating
                val testRuntimeOnlyDependenciesMetadata by configurations.creating

                configurations.getByName(commonTest.apiConfigurationName)
                    .extendsFrom(testApi)
                configurations.getByName(commonTest.implementationConfigurationName)
                    .extendsFrom(testImplementation)
                configurations.getByName(commonTest.compileOnlyConfigurationName)
                    .extendsFrom(testCompileOnly)
                configurations.getByName(commonTest.runtimeOnlyConfigurationName)
                    .extendsFrom(testRuntimeOnly)
                configurations.getByName(commonTest.apiMetadataConfigurationName)
                    .extendsFrom(testApiDependenciesMetadata)
                configurations.getByName(commonTest.implementationMetadataConfigurationName)
                    .extendsFrom(testImplementationDependenciesMetadata)
                configurations.getByName(commonTest.compileOnlyMetadataConfigurationName)
                    .extendsFrom(testCompileOnlyDependenciesMetadata)
                configurations.getByName(commonTest.runtimeOnlyMetadataConfigurationName)
                    .extendsFrom(testRuntimeOnlyDependenciesMetadata)
            }
        }
    }
}
