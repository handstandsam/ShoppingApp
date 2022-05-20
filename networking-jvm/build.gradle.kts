plugins {
    id("com.handstandsam.jvm.lib")
}
dependencies {
    api(project(":networking"))
    implementation(libs.kotlin.std.lib)
    implementation(libs.ktor.client)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)

    // JVM Only
    implementation(libs.ktor.client.okhttp)
}