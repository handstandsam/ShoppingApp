plugins {
    id("com.handstandsam.jvm.lib")
}

dependencies {
    implementation(libs.kotlin.std.lib)

    implementation(project(":models"))
    implementation(project(":networking"))
    implementation(project(":mock-data"))

}