plugins {
    id("com.handstandsam.kmp4free")
}

dependencies {
    api(project(":models"))

    //Kotlin
    api(libs.kotlin.std.lib)
    api(libs.kotlin.coroutines)

    //Unit Tests
    testImplementation(libs.junit)
    testImplementation(libs.assertj)
}
