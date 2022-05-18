plugins {
//    id("com.handstandsam.jvm.lib")
    kotlin("jvm")
}

dependencies {
    implementation(libs.kotlin.std.lib)

    implementation(project(":models"))
    implementation(project(":networking"))

}