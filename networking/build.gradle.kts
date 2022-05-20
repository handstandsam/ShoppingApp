plugins {
    id("com.handstandsam.kmp4free")
}
dependencies {
    api(libs.ktor.client)

    implementation(project(":models"))
    implementation(project(":mock-data"))
    implementation(libs.kotlin.std.lib)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.kotlinx.serialization.json)
}