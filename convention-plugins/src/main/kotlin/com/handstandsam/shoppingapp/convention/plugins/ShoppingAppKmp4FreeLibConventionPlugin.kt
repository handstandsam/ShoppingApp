package com.handstandsam.shoppingapp.convention.plugins

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import com.handstandsam.kmp4free.Kmp4FreePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ShoppingAppKmp4FreeLibConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        // Add Kmp4Free Plugin
        target.plugins.apply(Kmp4FreePlugin::class.java)

        // Apply Multiplatform Config if Available
        target.extensions.findByType(KotlinMultiplatformExtension::class.java)?.apply {
            val useIos = target.findProperty("ios") == "true"
            val useJs = target.findProperty("js") == "true"

            if (useIos) {
                iosSimulatorArm64 {
                    binaries.framework {
                        baseName = project.name
                    }
                }
            }
            if (useJs) {
                js(IR) {
                    browser()
                }
            }
        }

        setJvmVersionCompatibility(target)
    }


    companion object {
        fun setJvmVersionCompatibility(target: Project) {
            target.extensions.getByType(JavaPluginExtension::class.java).apply {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }
}
