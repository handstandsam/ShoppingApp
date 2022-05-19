plugins {
    id("com.handstandsam.multiplatform.lib")
}

kotlin {
    sourceSets {
        val commonMain = maybeCreate("commonMain").apply {
            dependencies {
                implementation(project(":mock-data"))
                implementation(project(":models"))
                implementation(libs.kotlin.std.lib)
                implementation(libs.ktor.client)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.kotlinx.serialization.json)

                // JVM Only
                implementation(libs.ktor.client.okhttp)
            }
        }
        val commonTest = maybeCreate("commonTest").apply {
            dependencies {
            }
        }
        val jvmMain = maybeCreate("jvmMain").apply {
            dependsOn(commonMain)
            dependencies {
//                implementation(libs.ktor.client.okhttp)
            }
        }
        val jvmTest = maybeCreate("jvmTest").apply {
            dependsOn(commonMain)
            dependencies {
            }
        }
    }
}