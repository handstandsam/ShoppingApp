plugins {
    kotlin("jvm")
    `java-library`
}

sourceSets {
    main {
        java {
            srcDir("src/commonMain/kotlin")
        }
    }
}

dependencies {
    implementation(kotlin("stdlib"))
}