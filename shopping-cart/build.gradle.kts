plugins {
    id("com.handstandsam.multiplatform.lib")
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
