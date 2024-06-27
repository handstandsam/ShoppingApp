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
            implementation(libs.androidx.compose.material)
            implementation(libs.androidx.compose.foundation)
            implementation(libs.androidx.compose.ui)

            implementation(project(":models"))
            implementation(project(":mock-data"))
            implementation(project(":shopping-cart"))
            implementation(project(":networking"))
            implementation(libs.kotlin.coroutines)
        }
    }
}
