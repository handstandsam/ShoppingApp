plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.2.0-alpha01-dev686"
}

kotlin {
    jvm {
        withJava()
    }

    val useIos = findProperty("kmp4free.ios") == "true"
    val useJs = findProperty("kmp4free.js") == "true"

    if (useIos) {
        listOf(
//            iosX64(),
//            iosArm64(),
            iosSimulatorArm64()
        ).forEach {
            it.binaries.framework {
                baseName = "multiplatform"
            }
        }
    }


    if (useJs) {
        js(IR) {
            browser {
                testTask {
                    enabled = false
                }
//                webpackTask {
//                    output.library = project.name
//                    output.libraryTarget = "window"
//                }
                binaries.executable()
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":models"))
                implementation(project(":mock-data"))
                implementation(project(":shopping-cart"))
                implementation(project(":networking"))
                implementation(libs.ktor.client)
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
                implementation(libs.ktor.client.okhttp)
            }
        }
        val jvmTest by getting {
            dependsOn(commonTest)
        }

        if (useJs) {
            val jsMain by getting {
                dependsOn(commonMain)
                dependencies {
                    implementation(libs.ktor.client.js)
                    implementation(compose.web.core)
                    implementation(compose.runtime)
                }
            }
            val jsTest by getting {
                dependsOn(commonTest)
            }
        }

        if (useIos) {
//            val iosX64Main by getting
//            val iosArm64Main by getting
            val iosSimulatorArm64Main by getting
            val iosMain by creating {
                dependsOn(commonMain)
//                iosX64Main.dependsOn(this)
//                iosArm64Main.dependsOn(this)
                iosSimulatorArm64Main.dependsOn(this)
                dependencies {
                    implementation(libs.ktor.client.darwin)
                }
            }
//            val iosX64Test by getting
//            val iosArm64Test by getting
            val iosSimulatorArm64Test by getting
            val iosTest by creating {
                dependsOn(commonTest)
//                iosX64Test.dependsOn(this)
//                iosArm64Test.dependsOn(this)
                iosSimulatorArm64Test.dependsOn(this)
            }
        }
    }
}