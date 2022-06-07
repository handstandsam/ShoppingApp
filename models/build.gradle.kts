plugins {
    id("shoppingapp.kmp4free.lib")
    kotlin("plugin.serialization") version "1.6.21"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.kotlinx.serialization.json)
}
