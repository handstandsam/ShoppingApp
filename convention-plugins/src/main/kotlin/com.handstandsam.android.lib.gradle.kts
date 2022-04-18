plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = ANDROID_COMPILE_SDK_VERSION

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    defaultConfig {
        minSdk = ANDROID_MIN_SDK_VERSION
        targetSdk = ANDROID_TARGET_SDK_VERSION
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(kotlin("stdlib"))
}
