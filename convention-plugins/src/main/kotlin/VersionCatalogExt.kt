import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

// https://github.com/gradle/gradle/issues/19813
internal fun Project.version(key: String): String = extensions
    .getByType<VersionCatalogsExtension>()
    .named("libs")
    .findVersion(key)
    .get()
    .requiredVersion

internal fun Project.versionInt(key: String) = version(key).toInt()

internal val Project.ANDROID_COMPILE_SDK_VERSION get() = versionInt("android.compile.sdk")
internal val Project.ANDROID_MIN_SDK_VERSION get() = versionInt("android.min.sdk")
internal val Project.ANDROID_TARGET_SDK_VERSION get() = versionInt("android.target.sdk")