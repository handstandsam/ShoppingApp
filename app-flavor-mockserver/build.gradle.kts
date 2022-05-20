plugins {
    id("com.handstandsam.jvm.lib")
}

dependencies {
    implementation(project(":mock-data"))
    implementation(project(":networking"))
    implementation(project(":networking-jvm"))
    implementation(project(":models"))

    implementation(libs.timber)
    implementation(libs.kotlin.std.lib)
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.std.lib)
    implementation(libs.kotlinx.serialization.json)

    api(libs.okhttp.logging.interceptor)
    api(libs.process.phoenix)
    api(libs.okhttp.mockwebserver)
}