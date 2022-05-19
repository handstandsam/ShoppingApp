plugins {
//    id("com.handstandsam.multiplatform.lib")
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":models"))
}

//kotlin {
//    jvm()
//    ios()
//    sourceSets {
//        commonMain {
//            dependencies {
//                implementation(kotlin("stdlib"))
//                implementation(project(":models"))
//            }
//        }
//        commonTest {
//            dependencies {
//            }
//        }
////        jvmMain {
////            dependencies {
////            }
////        }
////        jvmTest {
////            dependencies {
////            }
////        }
//    }
//}