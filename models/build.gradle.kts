plugins {
   id("com.handstandsam.multiplatform.lib")
    kotlin("plugin.serialization") version "1.6.21"
}

kotlin {
    sourceSets {
        val commonMain = maybeCreate("commonMain").apply {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(libs.kotlinx.serialization.json)
            }
        }
        val commonTest = maybeCreate("commonTest").apply {
            dependencies {
            }
        }
        val jvmMain  = maybeCreate("commonTest").apply {
            dependsOn(commonMain)
            dependencies {
            }
        }
        val jvmTest  = maybeCreate("commonTest").apply {
            dependsOn(commonMain)
            dependencies {
            }
        }
    }
}