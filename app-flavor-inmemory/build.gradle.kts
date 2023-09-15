plugins {
    id("shoppingapp.kmp4free.lib")
}

dependencies {
    implementation(project(":models"))
    implementation(project(":networking"))
    implementation(project(":mock-data"))
}
