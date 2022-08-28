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
                implementation(compose.desktop.currentOs)
                implementation(project(":compose-ui"))
                implementation(project(":mock-data"))
                implementation(project(":models"))
                implementation(project(":networking"))
                implementation(project(":shopping-cart"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.handstandsam.shoppingapp.compose.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ShoppingApp Desktop"
            packageVersion = "1.0.0"
        }
    }
}
