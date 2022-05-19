package com.handstandsam.convention.plugins

import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

/**
 * Allows us to use the SourceSet Structure of a Multiplatform Project in a JVM Project
 */
class SupportMultiplatformSourceSetForJvmPlugin(private val target: Project) {

    /**
     * Allow us to specify dependencies as "commonMain" or "commonTest"
     */
    fun enable() {
        val configurations = target.configurations
        target.extensions.getByType(KotlinJvmProjectExtension::class.java).apply {

            val main by sourceSets.getting {
                // Without this, would only have src/main/...
                kotlin.srcDirs("src/commonMain/java", "src/commonMain/kotlin")
            }
            setupCommonMainSourceSetConfigurations(main, configurations)

            // Enable us to specify jvmTest dependencies explicitly
            val test by sourceSets.getting {
                // Without this, would only have src/test/...
                kotlin.srcDirs("src/commonTest/java", "src/commonTest/kotlin")
            }
            setupJvmTestSourceSetConfigurations(test, configurations)
            setupCommonTestSourceSetConfigurations(test, configurations)
        }
    }

    /**
     * Make commonMain extend from main
     */
    private fun setupCommonMainSourceSetConfigurations(
        commonMain: KotlinSourceSet,
        configurations: ConfigurationContainer
    ) {
        val commonMainApi by configurations.creating
        val commonMainImplementation by configurations.creating
        configurations.getByName(commonMain.apiConfigurationName)
            .extendsFrom(commonMainApi)
        configurations.getByName(commonMain.implementationConfigurationName)
            .extendsFrom(commonMainImplementation)
    }

    /**
     * Make commonTest extend from test
     */
    private fun setupCommonTestSourceSetConfigurations(
        test: KotlinSourceSet,
        configurations: ConfigurationContainer
    ) {
        val commonTestApi by configurations.creating
        val commonTestImplementation by configurations.creating
        configurations.getByName(test.apiConfigurationName)
            .extendsFrom(commonTestApi)
        configurations.getByName(test.implementationConfigurationName)
            .extendsFrom(commonTestImplementation)
    }

    /**
     * Make jvmTest extend from test
     */
    private fun setupJvmTestSourceSetConfigurations(
        test: KotlinSourceSet,
        configurations: ConfigurationContainer
    ) {
        val jvmTestApi by configurations.creating
        val jvmTestImplementation by configurations.creating
        val jvmTestCompileOnly by configurations.creating
        val jvmTestRuntimeOnly by configurations.creating
        configurations.getByName(test.apiConfigurationName)
            .extendsFrom(jvmTestApi)
        configurations.getByName(test.implementationConfigurationName)
            .extendsFrom(jvmTestImplementation)
        configurations.getByName(test.compileOnlyConfigurationName)
            .extendsFrom(jvmTestCompileOnly)
        configurations.getByName(test.runtimeOnlyConfigurationName)
            .extendsFrom(jvmTestRuntimeOnly)
    }
}
