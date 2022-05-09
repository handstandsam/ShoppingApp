/**
 * The goal of this convention plugin is to support switching out from kotlin("jvm") without any other changes.
 */

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}