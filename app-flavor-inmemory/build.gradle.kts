plugins {
    id("shoppingapp.jvm.lib")
}

dependencies {
    implementation(project(":models"))
    implementation(project(":networking"))
    implementation(project(":mock-data"))
}
