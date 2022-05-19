plugins {
//    id("com.handstandsam.multiplatform.lib")
//    kotlin("jvm")
    id("com.handstandsam.jvm.lib")
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