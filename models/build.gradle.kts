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

// TODO Re-enable Multiplatform https://github.com/handstandsam/ShoppingApp/issues/39
//plugins {
//    kotlin("multiplatform")
//}
//
//kotlin {
//    jvm {
//        compilations.all {
//            kotlinOptions.jvmTarget = "1.8"
//        }
//        testRuns["test"].executionTask.configure {
//            useJUnit()
//        }
//    }
//
//    sourceSets {
//        val commonMain by getting {
//            dependencies {
//                implementation(kotlin("stdlib"))
//            }
//        }
//    }
//}