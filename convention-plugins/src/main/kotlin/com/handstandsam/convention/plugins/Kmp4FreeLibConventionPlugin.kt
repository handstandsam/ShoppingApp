package com.handstandsam.convention.plugins

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import com.handstandsam.kmp4free.Kmp4FreePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class Kmp4FreeLibConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val kmp4FreePlugin: Kmp4FreePlugin = target.plugins.apply(Kmp4FreePlugin::class.java)
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
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
        }
    }
}
