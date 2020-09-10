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
    val target_sdk = 29
    val compile_sdk = 29
    val build_tools = "29.0.3"

    // Kotlin
    val kotlin = "1.3.72"
    val kotlin_coroutines = "1.3.7"
    val apache_commons = "2.4"
    val retrofit_coroutines_adapter = "0.9.2"

    // SqlDelight
    val sql_delight = "1.4.3"

    val okhttp = "3.10.0"
    val glide = "4.11.0"
    val process_phoenix = "2.0.0"
    val junit = "4.12"
    val wiremock = "2.18.0"
    val timber = "4.5.1"
    val moshi = "1.6.0"

    // android
    val androidx = "1.2.0"
    val androidx_recyclerview = "1.1.0"
    val androidx_lifecycle = "2.2.0"
    val androidx_support_v4 = "1.0.0"
    val androidx_persistence = "2.2.5"
    val androidx_test = "1.1.0"

    val android_gradle_plugin = "4.2.0-alpha10"
    val espresso = "3.1.1"

    val assertj = "3.11.1"
}

object Libs {
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val retrofit_moshi_converter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    val process_phoenix = "com.jakewharton:process-phoenix:${Versions.process_phoenix}"

    //AndroidX
    val androidx_material = "com.google.android.material:material:${Versions.androidx}" //com.android.support:design
    val androidx_recyclerview = "androidx.recyclerview:recyclerview:${Versions.androidx_recyclerview}" //com.android.support:recyclerview-v7
    val androidx_appcompat_v7 = "androidx.appcompat:appcompat:${Versions.androidx}" //com.android.support:appcompat-v7

    val androidx_support_v4 = "androidx.legacy:legacy-support-v4:${Versions.androidx_support_v4}" //com.android.support:support-v4

    val androidx_persistence_room_runtime = "androidx.room:room-runtime:${Versions.androidx_persistence}" // Room
    val androidx_persistence_room_kapt = "androidx.room:room-compiler:${Versions.androidx_persistence}" // Annotation Processor

    val androidx_lifecycle_livedata_core = "androidx.lifecycle:lifecycle-livedata-core:${Versions.androidx_lifecycle}"
    val androidx_lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidx_lifecycle}"

    val androidx_persistence_room_ktx = "androidx.room:room-ktx:${Versions.androidx_persistence}" // optional - Kotlin Extensions and Coroutines support for Room
    val androidx_persistence_room_test = "androidx.room:room-testing:${Versions.androidx_persistence}" // Test helpers


    val androidx_espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    val androidx_espresso_contrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    val androidx_testrunner = "androidx.test:runner:${Versions.androidx_test}"
    val androidx_test_rules = "androidx.test:rules:${Versions.androidx_test}"
    val androidx_test_orchestrator = "androidx.test:orchestrator:${Versions.androidx_test}"

    val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    val okhttp_mockwebserver = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"
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

    val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    val apache_commons = "commons-io:commons-io:${Versions.apache_commons}"

    val sql_delight_android = "com.squareup.sqldelight:android-driver:${Versions.sql_delight}"
    val sql_delight_coroutines_extensions = "com.squareup.sqldelight:coroutines-extensions:${Versions.sql_delight}"
    val sql_delight_jvm = "com.squareup.sqldelight:sqlite-driver:${Versions.sql_delight}"
}

object TestLibs {
    val junit = "junit:junit:${Versions.junit}"
    val assertj = "org.assertj:assertj-core:${Versions.assertj}"
}

object GradlePlugins {
    val android = "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

private const val GROUP = "group"
private const val MODULE = "module"