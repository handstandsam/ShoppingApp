//import org.jetbrains.compose.compose

plugins {
    alias(libs.plugins.kotlin.multiplatform) // kotlin("jvm") doesn't work well in IDEA/AndroidStudio (https://github.com/JetBrains/compose-jb/issues/22)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvm {
        withJava()
    }
    val jvmMain by sourceSets.getting {
        dependencies {
            api(libs.androidx.material3)
            api(libs.androidx.compose.foundation)
            api(libs.androidx.compose.ui)

            implementation(project(":models"))
            implementation(project(":mock-data"))
            implementation(project(":shopping-cart"))
            implementation(project(":networking"))
            implementation(libs.kotlin.coroutines)
        }
    }
}
