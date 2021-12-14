plugins {
    id("java-library")
    id("kotlin")
}

dependencies {
    implementation(project(Modules.models))
    implementation(Libs.kotlin_std_lib)
}