plugins {
    kotlin("multiplatform")
}

kotlin {
    sourceSets {
        val main by creating {
            kotlin.srcDir("src/main/java")
        }
        val commonMain by getting {
            dependsOn(main)
        }
    }

    jvm {}
}
