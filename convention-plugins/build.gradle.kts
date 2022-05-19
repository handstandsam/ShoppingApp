plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.android.gradle.plugin)
    compileOnly(gradleApi())
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(8)
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
            "com.handstandsam.jvm.lib",
            "com.handstandsam.convention.plugins.JvmLibConventionPlugin"
        )
        createPlugin(
            "com.handstandsam.android.lib",
            "com.handstandsam.AndroidLibConventionPlugin"
        )
        createPlugin(
            "com.handstandsam.multiplatform.lib",
            "com.handstandsam.convention.plugins.KotlinMultiplatformConventionPlugin"
        )
    }
}
