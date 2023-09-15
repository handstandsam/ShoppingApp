plugins {
    id("shoppingapp.jvm.lib")
}
dependencies {
    api(project(":networking"))
    implementation(libs.ktor.client)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)

    // JVM Only
    implementation(libs.ktor.client.okhttp)
}