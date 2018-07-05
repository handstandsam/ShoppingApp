// https://github.com/handstandsam/ShoppingApp/tree/master/buildSrc

object Projects {
    val models = ":models"
    val mockData = ":mock-data"
    val sample = ":sample"
}

object Versions {
    val retrofit = "2.3.0"
    val rxjava = "2.1.9"
    val source_compat = "1.8"
    val target_compat = "1.8"
    val minSdk = 21
    val targetSdk = 27
    val compileSdk = 27
    val buildTools = "27.0.3"


    // Kotlin
    val kotlin = "1.2.51"
    val kotlin_coroutines = "0.22.1"

    val stetho = "1.5.0"
    val okhttp = "3.10.0"
    val rx_android = "2.0.2"
    val glide = "4.1.1"
    val dagger = "2.13"
    val process_phoenix = "2.0.0"
    val chuck = "1.0.4"
    val junit = "4.12"
    val wiremock = "2.8.0"
    val timber = "4.5.1"
    val rx_java = "2.1.16"
    val moshi = "1.5.0"

    // android
    val support_lib = "27.1.1"
    val android_gradle_plugin = "3.1.3"
    val espresso = "3.0.1"
    val test_support_lib = "1.0.1"
}

object Libs {
    val support_annotations = "com.android.support:support-annotations:${Versions.support_lib}"
    val support_appcompat_v7 = "com.android.support:appcompat-v7:${Versions.support_lib}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofit_rxjava_adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val rxjava2 = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    val rx_android = "io.reactivex.rxjava2:rxandroid:${Versions.rx_android}"
    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val retrofit_moshi_converter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    val processPhoenix = "com.jakewharton:process-phoenix:${Versions.process_phoenix}"

    val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    val daggerKapt = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    val okHttp3LoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    val okHttp3MockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"
    val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val retrofitAdapterRxJava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    val wiremock = "com.github.tomakehurst:wiremock:${Versions.wiremock}"
    val apacheHttpClientAndroid = "org.apache.httpcomponents:httpclient-android:4.3.5.1"
    val rxJava2 = "io.reactivex.rxjava2:rxjava:${Versions.rx_android}"

    val runner = "com.android.support.test:runner:1.0.1"
    val orchestrator = "com.android.support.test:orchestrator:1.0.1"
    val espressoCore = "com.android.support.test.espresso:espresso-core:3.0.1"
    val espressoContrib = "com.android.support.test.espresso:espresso-contrib:3.0.1"

    val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_coroutines}"
    val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines}"

    val junit = "junit:junit:${Versions.junit}"

    val compat = "com.android.support:support-compat:${Versions.support_lib}"
    val coreUtils = "com.android.support:support-core-utils:${Versions.support_lib}"
    val percent = "com.android.support:percent:${Versions.support_lib}"
    val supportAnnotations = "com.android.support:support-annotations:${Versions.support_lib}"
    val supportFragment = "com.android.support:support-fragment:${Versions.support_lib}"
    val supportV4 = "com.android.support:support-v4:${Versions.support_lib}"
    val supportV13 = "com.android.support:support-v13:${Versions.support_lib}"
    val supportAppCompatV7 = "com.android.support:appcompat-v7:${Versions.support_lib}"
    val supportRecyclerView = "com.android.support:recyclerview-v7:${Versions.support_lib}"
    val supportCardView = "com.android.support:cardview-v7:${Versions.support_lib}"
    val supportGridLayout = "com.android.support:gridlayout-v7:${Versions.support_lib}"
    val supportDesign = "com.android.support:design:${Versions.support_lib}"
    val supportPreferenceV7 = "com.android.support:preference-v7:${Versions.support_lib}"
    val supportPreferenceV14 = "com.android.support:preference-v14:${Versions.support_lib}"
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

    val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
    val stethoOkHttp3 = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"
    val chuck = "com.readystatesoftware.chuck:library:${Versions.chuck}"
    val chuckNoOp = "com.readystatesoftware.chuck:library-no-op:${Versions.chuck}"
}

object TestLibs {
    val espresso = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
    val espressoIntents = "com.android.support.test.espresso:espresso-intents:${Versions.espresso}"
    val androidTestRunner = "com.android.support.test:runner:${Versions.test_support_lib}"
    val testRules = "com.android.support.test:rules:${Versions.test_support_lib}"
}

object GradlePlugins {
    val android = "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object Modules {
    val supportAnnotations = mapOf(
        GROUP to "com.android.support",
        MODULE to "support-annotations"
    )
}

private const val GROUP = "group"
private const val MODULE = "module"