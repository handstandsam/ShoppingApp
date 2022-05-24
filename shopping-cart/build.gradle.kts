plugins {
    id("com.handstandsam.kmp4free")
}

kotlin {
    sourceSets {
        maybeCreate("commonMain").apply {
            dependencies {
                api(project(":models"))

                //Kotlin
                api(libs.kotlin.std.lib)
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
