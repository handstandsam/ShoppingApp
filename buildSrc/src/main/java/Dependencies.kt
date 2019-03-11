// https://github.com/handstandsam/ShoppingApp/tree/master/buildSrc

object Projects {
    val models = ":models"
    val mock_data = ":mock-data"
    val sample = ":sample"
}

object Versions {
    val retrofit = "2.3.0"
    val rxjava = "2.1.9"
    val source_compat = "1.8"
    val target_compat = "1.8"
    val min_sdk = 21
    val target_sdk = 27
    val compile_sdk = 27
    val build_tools = "28.0.3"

    // Kotlin
    val kotlin = "1.3.21"
    val kotlin_coroutines = "0.26.1"
    val apache_commons = "2.4"

    val stetho = "1.5.0"
    val okhttp = "3.10.0"
    val rx_android = "2.0.2"
    val glide = "4.7.1"
    val dagger = "2.16"
    val process_phoenix = "2.0.0"
    val chuck = "1.1.0"
    val junit = "4.12"
    val wiremock = "2.18.0"
    val timber = "4.5.1"
    val rx_java = "2.1.16"
    val moshi = "1.6.0"

    // android
    val support_lib = "27.1.1"
    val android_gradle_plugin = "3.2.1"
    val espresso = "3.0.1"
    val test_support_lib = "1.0.1"
}

object Libs {
    val support_recycler_view = "com.android.support:recyclerview-v7:${Versions.support_lib}"
    val support_annotations = "com.android.support:support-annotations:${Versions.support_lib}"
    val support_appcompat_v7 = "com.android.support:appcompat-v7:${Versions.support_lib}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofit_rxjava_adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val rxjava2 = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    val rx_android = "io.reactivex.rxjava2:rxandroid:${Versions.rx_android}"
    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val retrofit_moshi_converter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    val process_phoenix = "com.jakewharton:process-phoenix:${Versions.process_phoenix}"
    val support_v4 = "com.android.support:support-v4:${Versions.support_lib}"
    val support_design = "com.android.support:design:${Versions.support_lib}"

    val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    val dagger_kapt = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    val okhttp_mockwebserver = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"
    val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val retrofit_adapter_rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    val wiremock = "com.github.tomakehurst:wiremock:${Versions.wiremock}"
    val apacheHttpClientAndroid = "org.apache.httpcomponents:httpclient-android:4.3.5.1"
    val kotlin_std_lib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    val kotlin_coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_coroutines}"
    val kotlin_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines}"

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
}

object TestLibs {
    val runner = "com.android.support.test:runner:${Versions.test_support_lib}"
    val orchestrator = "com.android.support.test:orchestrator:${Versions.test_support_lib}"
    val espresso_core = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
    val espresso_contrib = "com.android.support.test.espresso:espresso-contrib:${Versions.espresso}"
    val android_test_runner = "com.android.support.test:runner:${Versions.test_support_lib}"
    val testRules = "com.android.support.test:rules:${Versions.test_support_lib}"
    val junit = "junit:junit:${Versions.junit}"
}

object GradlePlugins {
    val android = "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object Modules {
    val support_annotations = mapOf(
        GROUP to "com.android.support",
        MODULE to "support-annotations"
    )
}

private const val GROUP = "group"
private const val MODULE = "module"