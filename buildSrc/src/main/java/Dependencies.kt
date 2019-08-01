// https://github.com/handstandsam/ShoppingApp/tree/master/buildSrc

object Modules {
    val models = ":models"
    val mockData = ":mock-data"
    val sample = ":sample"
    val networking = ":networking"
    val debug = ":debug"
    val shoppingCart = ":shopping-cart"
    val shoppingCartRoom = ":shopping-cart-room"
    val shoppingCartSqldelight = ":shopping-cart-sqldelight"
    val flavorInmemory = ":app-flavor-inmemory"
    val flavorMockserver = ":app-flavor-mockserver"
    val flavorLiveserver = ":app-flavor-liveserver"
}

object Versions {
    val retrofit = "2.3.0"
    val source_compat = "1.8"
    val target_compat = "1.8"
    val min_sdk = 21
    val target_sdk = 28
    val compile_sdk = 28
    val build_tools = "28.0.3"

    // Kotlin
    val kotlin = "1.3.21"
    val kotlin_coroutines = "1.2.0-alpha"
    val apache_commons = "2.4"
    val retrofit_coroutines_adapter = "0.9.2"

    // SqlDelight
    val sql_delight = "1.1.1"

    val stetho = "1.5.0"
    val okhttp = "3.10.0"
    val glide = "4.9.0"
    val dagger = "2.16"
    val process_phoenix = "2.0.0"
    val chuck = "1.1.0"
    val junit = "4.12"
    val wiremock = "2.18.0"
    val timber = "4.5.1"
    val moshi = "1.6.0"

    // android
    val androidx = "1.0.0"
    val androidx_lifecycle = "2.0.0"
    val androidx_persistence = "2.2.0-alpha01"
    val androidx_test = "1.1.0"

    val android_gradle_plugin = "3.4.2"
    val espresso = "3.0.1"
    val test_support_lib = "1.0.1"

    val assertj = "3.11.1"
    val android_debug_database = "1.0.6"
}

object Libs {
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val retrofit_moshi_converter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    val process_phoenix = "com.jakewharton:process-phoenix:${Versions.process_phoenix}"

    //AndroidX
    val androidx_material = "com.google.android.material:material:${Versions.androidx}" //com.android.support:design
    val androidx_recyclerview = "androidx.recyclerview:recyclerview:${Versions.androidx}" //com.android.support:recyclerview-v7
    val androidx_appcompat_v7 = "androidx.appcompat:appcompat:${Versions.androidx}" //com.android.support:appcompat-v7
    val androidx_support_v4 = "androidx.legacy:legacy-support-v4:${Versions.androidx}" //com.android.support:support-v4
    val androidx_annotations = "androidx.annotation:annotation:${Versions.androidx}" //com.android.support:support-annotations


    val androidx_persistence_room_runtime = "androidx.room:room-runtime:${Versions.androidx_persistence}" // Room
    val androidx_persistence_room_kapt = "androidx.room:room-compiler:${Versions.androidx_persistence}" // Annotation Processor

    val androidx_lifecycle_livedata_core = "androidx.lifecycle:lifecycle-livedata-core:${Versions.androidx_lifecycle}"

    val androidx_persistence_room_ktx = "androidx.room:room-ktx:${Versions.androidx_persistence}" // optional - Kotlin Extensions and Coroutines support for Room
    val androidx_persistence_room_test = "androidx.room:room-testing:${Versions.androidx_persistence}" // Test helpers


    val androidx_testrunner = "androidx.test:runner:${Versions.androidx_test}"
    val androidx_test_rules = "androidx.test:rules:${Versions.androidx_test}"

    val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    val dagger_kapt = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    val okhttp_mockwebserver = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"
    val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    val wiremock = "com.github.tomakehurst:wiremock:${Versions.wiremock}"
    val apacheHttpClientAndroid = "org.apache.httpcomponents:httpclient-android:4.3.5.1"
    val kotlin_std_lib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    val kotlin_coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_coroutines}"
    val kotlin_coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines}"
    val retrofit_coroutines_adapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofit_coroutines_adapter}"
    val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_coroutines}"
    val coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines}"

    val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
    val stetho_okhttp3 = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"
    val chuck = "com.readystatesoftware.chuck:library:${Versions.chuck}"
    val chuck_no_op = "com.readystatesoftware.chuck:library-no-op:${Versions.chuck}"
    val apache_commons = "commons-io:commons-io:${Versions.apache_commons}"
    val android_debug_database = "com.amitshekhar.android:debug-db:${Versions.android_debug_database}"

    val sql_delight_android = "com.squareup.sqldelight:android-driver:${Versions.sql_delight}"
    val sql_delight_jvm = "com.squareup.sqldelight:sqlite-driver:${Versions.sql_delight}"
}

object TestLibs {
    val runner = "com.android.support.test:runner:${Versions.test_support_lib}"
    val orchestrator = "com.android.support.test:orchestrator:${Versions.test_support_lib}"
    val espresso_core = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
    val espresso_contrib = "com.android.support.test.espresso:espresso-contrib:${Versions.espresso}"
    val android_test_runner = "com.android.support.test:runner:${Versions.test_support_lib}"
    val testRules = "com.android.support.test:rules:${Versions.test_support_lib}"
    val junit = "junit:junit:${Versions.junit}"
    val assertj = "org.assertj:assertj-core:${Versions.assertj}"
}

object GradlePlugins {
    val android = "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

private const val GROUP = "group"
private const val MODULE = "module"