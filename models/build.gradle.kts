plugins {
   id("com.handstandsam.multiplatform.lib")
//    kotlin("jvm")
//     kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.6.21"
}

kotlin {
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(libs.kotlinx.serialization.json)
            }
        }
        val commonTest by getting {
            dependencies {
            }
        }
        val jvmMain by getting {
            dependsOn(commonMain)
            dependencies {
            }
        }
        val jvmTest by getting {
            dependsOn(commonMain)
            dependencies {
            }
        }
    }
}