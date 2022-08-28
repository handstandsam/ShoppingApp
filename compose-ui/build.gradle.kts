import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform") // kotlin("jvm") doesn't work well in IDEA/AndroidStudio (https://github.com/JetBrains/compose-jb/issues/22)
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        withJava()
    }
    val jvmMain by sourceSets.getting {
        dependencies {
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.material)

            implementation(project(":models"))
            implementation(project(":mock-data"))
            implementation(project(":shopping-cart"))
            implementation(project(":networking"))
            implementation(libs.kotlin.coroutines)
        }
    }
}
