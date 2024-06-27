plugins {
    id("com.android.application")
    id("kotlin-android")
    alias(libs.plugins.dependency.guard)
    alias(libs.plugins.compose.compiler)
}

android {
    compileSdk = Integer.parseInt(libs.versions.android.compile.sdk.get())

    namespace = "com.handstandsam.shoppingapp"

    defaultConfig {
        applicationId = "com.handstandsam.shoppingapp"
        minSdk = Integer.parseInt(libs.versions.android.min.sdk.get())
        targetSdk = Integer.parseInt(libs.versions.android.target.sdk.get())
        versionCode = 1
        versionName = "1.0"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        animationsDisabled = true
    }

    androidComponents {
        beforeVariants { variantBuilder ->
            if (variantBuilder.buildType != "debug") {
                variantBuilder.enable = false
            }
        }
    }

    lint {
        baseline = file("lint-baseline.xml")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(project(":compose-ui"))

    implementation(project(":models"))
    implementation(project(":mock-data"))
    implementation(project(":shopping-cart"))

    // AndroidX Libraries
    implementation(libs.androidx.appcompat.v7)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Networking)
    implementation(libs.glide)
    implementation(libs.okhttp)
    implementation(project(":networking"))
    debugImplementation(libs.okhttp.logging.interceptor)

    // Kotlin)
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.coroutines.android)

    //Flavor Modules)
    implementation(project(":app-flavor-inmemory"))
//  implementation( project(":app-flavor-mockserver"))
//  implementation( project(":app-flavor-liveserver"))

    //Logging
    implementation(libs.timber)

    // Compose
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.activity.compose)
    implementation(libs.accompanist.coil)
    implementation(libs.coil.compose)
    implementation(libs.landscapist.coil)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //JVM Tests Only
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.espresso)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.testrunner)
    androidTestImplementation(project(":app-test-suite"))
}

dependencyGuard {
    configuration("debugRuntimeClasspath") {
        modules = true
    }
}
