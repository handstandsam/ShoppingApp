plugins {
    id("com.handstandsam.jvm.lib")
}

dependencies {
    implementation(libs.kotlin.std.lib)
    implementation(libs.okhttp)

    implementation(project(":models"))
    implementation(project(":networking"))

}