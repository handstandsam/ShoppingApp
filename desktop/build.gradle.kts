import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
//    alias(libs.plugins.kotlin.multiplatform)
    kotlin("jvm")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.compose)
    application
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(project(":compose-ui"))
    implementation(project(":mock-data"))
    implementation(project(":models"))
    implementation(project(":networking"))
    implementation(project(":shopping-cart"))
}


//compose.desktop {
//}


application {
mainClass = "com.handstandsam.shoppingapp.compose.MainKt"

//    nativeDistributions {
//        targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
//        packageName = "ShoppingApp Desktop"
//        packageVersion = "1.0.0"
//    }
}