plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {
        withJava()
    }

    val useIos = findProperty("ios") == "true"
    val useJs = findProperty("js") == "true"

    if (useIos) {
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        ).forEach {
            it.binaries.framework {
                baseName = "multiplatform"
//           export(project(":models"))
//            export(project(":mock-data"))
//            export(project(":shopping-cart"))
                // Export transitively.
                // transitiveExport = true
            }
        }
    }


    if (useJs) {
        js(IR) {
            browser {
                testTask {
                    enabled = false
                }
                webpackTask {
                    output.library = project.name
                    output.libraryTarget = "window"
                }
                binaries.executable()
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":models"))
                api(project(":mock-data"))
//                api(project(":shopping-cart"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependsOn(commonMain)
            dependencies {
            }
        }
        val jvmTest by getting {
            dependsOn(commonTest)
        }

        if (useJs) {
            val jsMain by getting {
                dependsOn(commonMain)
                dependencies {
                }
            }
            val jsTest by getting {
                dependsOn(commonTest)
            }
        }

        if (useIos) {
            val iosX64Main by getting
            val iosArm64Main by getting
            val iosSimulatorArm64Main by getting
            val iosMain by creating {
                dependsOn(commonMain)
                iosX64Main.dependsOn(this)
                iosArm64Main.dependsOn(this)
                iosSimulatorArm64Main.dependsOn(this)
            }
            val iosX64Test by getting
            val iosArm64Test by getting
            val iosSimulatorArm64Test by getting
            val iosTest by creating {
                dependsOn(commonTest)
                iosX64Test.dependsOn(this)
                iosArm64Test.dependsOn(this)
                iosSimulatorArm64Test.dependsOn(this)
            }
        }
    }
}