//import org.jetbrains.compose.compose

plugins {
    alias(libs.plugins.kotlin.multiplatform) // kotlin("jvm") doesn't work well in IDEA/AndroidStudio (https://github.com/JetBrains/compose-jb/issues/22)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.compose)
}

kotlin {
    jvm {
        withJava()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.foundation)
                implementation(compose.material3)
            }
        }
        val jvmMain by getting {
            dependencies {
                api(project(":models"))
                api(project(":mock-data"))
                api(project(":shopping-cart"))
                api(project(":networking"))
                api(libs.kotlin.coroutines)
            }
        }
    }
}
