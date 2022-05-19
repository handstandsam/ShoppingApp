package com.handstandsam.convention.plugins

import com.handstandsam.ConventionPluginsGradlePropertyValues
import org.gradle.api.Action
import org.gradle.api.GradleException
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin

/**
 * Allows us to use the SourceSet Structure of a JVM Project in a Multiplatform Project
 */
class SupportJvmSourceSetForMultiplatformPlugin(private val target: Project) {

    private val gradleProperties = ConventionPluginsGradlePropertyValues(target)

    private val multiplatformExtension =
        target.extensions.getByType(KotlinMultiplatformExtension::class.java)

    /**
     * Make commonMain extend from main
     */
    private fun setupMainSourceSetConfigurations(
        commonMain: KotlinSourceSet
    ) {
        val configurations = target.configurations

        val api = configurations.maybeCreate("api")
        val implementation = configurations.maybeCreate("implementation")
        val compileOnly = configurations.maybeCreate("compileOnly")
        val runtimeOnly = configurations.maybeCreate("runtimeOnly")

        val apiDependenciesMetadata =
            configurations.maybeCreate("apiDependenciesMetadata")
        val implementationDependenciesMetadata =
            configurations.maybeCreate("implementationDependenciesMetadata")
        val compileOnlyDependenciesMetadata =
            configurations.maybeCreate("compileOnlyDependenciesMetadata")
        val runtimeOnlyDependenciesMetadata =
            configurations.maybeCreate("runtimeOnlyDependenciesMetadata")

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
    }

    /**
     * Make commonTest extend from test
     */
    private fun setupTestSourceSetConfigurations(
        commonTest: KotlinSourceSet
    ) {
        val configurations = target.configurations

        val testApi = configurations.maybeCreate("testApi")
        val testImplementation = configurations.maybeCreate("testImplementation")
        val testCompileOnly = configurations.maybeCreate("testCompileOnly")
        val testRuntimeOnly = configurations.maybeCreate("testRuntimeOnly")

        configurations.getByName(commonTest.apiConfigurationName)
            .extendsFrom(testApi)
        configurations.getByName(commonTest.implementationConfigurationName)
            .extendsFrom(testImplementation)
        configurations.getByName(commonTest.compileOnlyConfigurationName)
            .extendsFrom(testCompileOnly)
        configurations.getByName(commonTest.runtimeOnlyConfigurationName)
            .extendsFrom(testRuntimeOnly)
        configurations.getByName(commonTest.apiMetadataConfigurationName)
    }

    /**
     * https://youtrack.jetbrains.com/issue/KT-49109
     * Workaround until Kotlin 1.6.20
     */
    private fun enableJavaScriptTarget() {
        if (target.getKotlinPluginVersion() == "1.6.10") {
            target.rootProject.plugins.withType(NodeJsRootPlugin::class.java) {
                target.rootProject.extensions.getByType(NodeJsRootExtension::class.java).nodeVersion =
                    "16.0.0"
            }
        } else {
            throw GradleException("You are using version '${target.getKotlinPluginVersion()}' of Kotlin, so you no longer need this workaround")
        }
        multiplatformExtension.js {
            nodejs()
        }
    }

    /**
     * Configures the [sourceSets] of the [KotlinSourceSet] extension.
     */
    private fun KotlinMultiplatformExtension.sourceSets(
        configure: Action<NamedDomainObjectContainer<KotlinSourceSet>>
    ): Unit = (this as org.gradle.api.plugins.ExtensionAware).extensions
        .configure("sourceSets", configure)

    fun enable() {
        multiplatformExtension.apply {
            // Always enable JVM
            jvm {
                withJava()
            }
            if (gradleProperties.isIosEnabled) {
                println("Enabling iOS Multiplatform Target for ${target.path}")
                multiplatformExtension.apply {
                    ios()
                }
            }
            if (gradleProperties.isJsEnabled) {
                println("Enabling JavaScript Multiplatform Target for ${target.path}")
                enableJavaScriptTarget()
            }
        }

        configureSourceSets()
        createTestTaskAliasToJvmTest()
        JvmLibConventionPlugin.setJvmVersionCompatibility(target)
    }

    private fun configureSourceSets() {
        multiplatformExtension.sourceSets {
            // Ensure commonMain uses Sources from src/main
            val commonMain by getting {
                kotlin.srcDirs("src/main/java", "src/main/kotlin")
            }

            // Map all "main" configuration to "commonMain"
            setupMainSourceSetConfigurations(commonMain)

            // Map all "test" configuration to "jvmTest"
            val jvmTest by getting {
                // Without this, would only have src/jvmTest/...
                kotlin.srcDirs("src/test/java", "src/test/kotlin")
            }
            setupTestSourceSetConfigurations(jvmTest)

            // Ensure commonTest uses Sources from src/test
            val commonTest by getting {
                // Without this, would only have src/commonTest/...
                kotlin.srcDirs("src/test/java", "src/test/kotlin")
            }
        }
    }

    /**
     * Create a "test" task that will map to "jvmTest"
     */
    private fun createTestTaskAliasToJvmTest() {
        if (target.tasks.findByName("test") == null) {
            target.tasks.create("test").apply {
                dependsOn(target.tasks.findByName("jvmTest"))
                group = "Verification"
                description = "Alias for jvmTest"
            }
        }
    }
}
