plugins {
    id("shoppingapp.jvm.lib")
}

dependencies {
    implementation(libs.okhttp)

    implementation(project(":models"))
    implementation(project(":networking"))
    implementation(project(":networking-jvm"))

}