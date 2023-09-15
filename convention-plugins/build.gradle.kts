plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.android.gradle.plugin)
    implementation(libs.kmp4free)
    compileOnly(gradleApi())
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(17)
}

gradlePlugin {
    plugins {
        fun createPlugin(id: String, className: String) {
            plugins.create(id) {
                this.id = id
                implementationClass = className
            }
        }
        createPlugin(
            "shoppingapp.kmp4free.lib",
            "com.handstandsam.shoppingapp.convention.plugins.ShoppingAppKmp4FreeLibConventionPlugin"
        )
        createPlugin(
            "shoppingapp.jvm.lib",
            "com.handstandsam.shoppingapp.convention.plugins.ShoppingAppJvmLibConventionPlugin"
        )
        createPlugin(
            "shoppingapp.android.lib",
            "com.handstandsam.shoppingapp.convention.plugins.ShoppingAppAndroidLibConventionPlugin"
        )
    }
}
