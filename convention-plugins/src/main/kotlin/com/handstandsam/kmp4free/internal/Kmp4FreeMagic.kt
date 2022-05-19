package com.handstandsam.kmp4free.internal

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
class Kmp4FreeMagic(private val target: Project) {

    private val gradleProperties = Kmp4FreePropertyValues(target)

    private val multiplatformExtension =
        target.extensions.getByType(KotlinMultiplatformExtension::class.java)

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
                    iosX64()
                    iosArm64()
                    iosSimulatorArm64()
                }
            }
            if (gradleProperties.isJsEnabled) {
                println("Enabling JavaScript Multiplatform Target for ${target.path}")
                multiplatformExtension.js {
                    browser()
                }
            }
        }

        configureSourceSets()
        createTestTaskAliasToJvmTest()
    }

    private fun configureSourceSets() {
        multiplatformExtension.sourceSets {
            // Ensure commonMain uses Sources from src/main
            listOf(
                "commonMain",
                "jvmMain"
            ).forEach {
                maybeCreate(it).apply {
                    kotlin.srcDirs("src/main/java", "src/main/kotlin")
                }
            }

            listOf(
                "commonTest",
                "jvmTest"
            ).forEach {
                maybeCreate(it).apply {
                    kotlin.srcDirs("src/test/java", "src/test/kotlin")
                }
            }

            // Extend SourceSets
            Kmp4FreeSourceSetMagic(target).apply {
                extendSourceSet("commonMain", "main")
                extendSourceSet("jvmTest", "test")
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
