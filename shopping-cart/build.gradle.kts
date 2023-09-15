plugins {
    id("shoppingapp.kmp4free.lib")
}

kotlin {
    sourceSets {
        maybeCreate("commonMain").apply {
            dependencies {
                api(project(":models"))

                //Kotlin
                api(libs.kotlin.coroutines)
            }
        }
        maybeCreate("jvmTest").apply {
            dependencies {
                implementation(libs.junit)
                implementation(libs.assertj)
            }
        }
    }
}
