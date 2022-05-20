plugins {
    id("com.handstandsam.jvm.lib")
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

    // JVM Only
    implementation(libs.ktor.client.okhttp)
}