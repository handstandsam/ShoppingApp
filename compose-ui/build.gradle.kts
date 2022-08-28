import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform") // kotlin("jvm") doesn't work well in IDEA/AndroidStudio (https://github.com/JetBrains/compose-jb/issues/22)
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        withJava()
    }
    sourceSets {
        named("jvmMain") {
            dependencies {
                compileOnly(compose.ui)
                compileOnly(compose.foundation)
                compileOnly(compose.material)
                implementation(libs.kotlin.coroutines)
                implementation(project(":models"))
                implementation(project(":mock-data"))
                implementation(project(":shopping-cart"))
                implementation(project(":networking"))
            }
        }
    }
}
