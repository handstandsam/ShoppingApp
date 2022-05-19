plugins {
//    id("com.handstandsam.multiplatform.lib")
    kotlin("jvm")
}
dependencies {

    implementation(project(":mock-data"))
    implementation(project(":models"))
    implementation(libs.kotlin.std.lib)
    implementation(libs.ktor.client)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.okhttp)
}
//kotlin {
//    sourceSets {
//        val commonMain by getting {
//            dependencies {
//                implementation(project(":mock-data"))
//                implementation(project(":models"))
//                implementation(libs.kotlin.std.lib)
//                implementation(libs.ktor.client)
//                implementation(libs.ktor.client.logging)
//                implementation(libs.ktor.client.content.negotiation)
//                implementation(libs.ktor.serialization.kotlinx.json)
//                implementation(libs.kotlinx.serialization.json)
//            }
//        }
//        val commonTest by getting {
//            dependencies {
//            }
//        }
//        val jvmMain by getting {
//            dependsOn(commonMain)
//            dependencies {
//                implementation(libs.ktor.client.okhttp)
//            }
//        }
//        val jvmTest by getting {
//            dependsOn(commonMain)
//            dependencies {
//            }
//        }
//    }
//}