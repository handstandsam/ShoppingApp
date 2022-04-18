plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":models"))
}
