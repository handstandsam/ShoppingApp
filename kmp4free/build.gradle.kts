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
            "com.handstandsam.kmp4free",
            "com.handstandsam.kmp4free.Kmp4FreePlugin"
        )
    }
}
