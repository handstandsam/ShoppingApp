plugins {
    id("shoppingapp.kmp4free.lib")
}
dependencies {
    api(libs.ktor.client)

    implementation(project(":models"))
    implementation(project(":mock-data"))
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.kotlinx.serialization.json)
}