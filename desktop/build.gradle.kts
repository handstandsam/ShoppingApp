plugins {
  kotlin("multiplatform")
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.jetbrains.compose)
  alias(libs.plugins.dependency.guard)
}
dependencyGuard {
  configuration("desktopRuntimeClasspath") {
    baselineMap = {
      if (it.contains("linux-x64") || it.contains("macos-arm64")) {
        it.replace("macos-arm64", "<platform>").replace("linux-x64", "<platform>")
      } else {
        it
      }
    }
  }
}

kotlin {
  jvm("desktop")
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(compose.foundation)
        implementation(compose.material3)
        implementation(project(":mock-data"))
        implementation(project(":models"))
        implementation(project(":networking"))
        implementation(project(":shopping-cart"))
      }
    }

    val desktopMain by getting {
      dependsOn(commonMain)
      dependencies {
        implementation(compose.desktop.currentOs)


//                implementation(compose.desktop.currentOs)
        implementation(project(":compose-ui"))
//                implementation("androidx.compose.ui:ui-desktop-:1.7.0-beta02")
//                implementation("androidx.compose.material3:material3-window-size-class:1.3.0-beta04")
//                implementation("org.jetbrains.compose.desktop:desktop-jvm-macos-arm64:1.6.11")
//                implementation("androidx.compose.material3:material3:1.3.0-beta04")
//                implementation("androidx.compose.material:material:1.3.0-beta04")
//                implementation("androidx.compose.material3:material3-window-size-class:1.3.0-beta04")
//                api(libs.androidx.material3.desktop)
      }
    }
  }
}
compose.desktop {


  application {
    mainClass = "com.handstandsam.shoppingapp.compose.MainKt"

//    nativeDistributions {
//        targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
//        packageName = "ShoppingApp Desktop"
//        packageVersion = "1.0.0"
//    }
  }
}