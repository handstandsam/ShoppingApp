# 🪄 KMP4FREE
Allows you to toggle between Kotlin JVM Plugin -> Kotlin Multiplatform with a Gradle Property `kmp4free.multiplatform=true`.

This Gradle Plugin was built to support gradual adoption of Kotlin Multiplatform.  It's called `KMP4FREE` because you are able to take a normal Kotlin JVM module, and with a single line change, enable or disable the Kotlin Multiplatform Plugin.

There is always a risk of adding something new, especially with a large project. The introduction of a technology that is not at a stable version yet can block the adoption of a new technology.  The goal of this plugin is to reduce risk to a single line change.

## Plugin Installation
This Gradle plugin is not being distributed as a binary at this point, so you will need to move the code into your own repository.

Just replace `kotlin("jvm")` with `id("com.handstandsam.kmp4free")` in the `plugins` block of your module's `build.gradle.kts` file.

## Multiplatform Enabled (Uses Kotlin Multiplatform Plugin)
You can set `kmp4free.multiplatform=true` in your `gradle.properties` or send it in as a command-line parameter to gradle with `-Pkmp4free.multiplatform=true`.

This enables property the Kotlin Multiplatform Plugin, along with the additional changes required to support seamless switching between the Kotlin JVM Plugin.

Any code in the `main` sourceSet needs to be `commonMain` compatible when `multiplatform` is enabled, otherwise the build will fail about not being able to resolve JVM dependencies.  This isn't a bad thing though, as you will then be able to identify what you need to change to make your code multiplatform compatible.

#### SourceSet Mapping
* `src/main` ➡️ `src/commonMain`
* `src/test` ➡️ `src/jvmTest`

#### Configuration Mapping
* `implementation` ➡️ `commonMainImplementation`
* `api` ➡️ `commonMainApi`
* `testImplementation` ➡️ `jvmTestImplementation`
* `testApi` ➡️ `jvmTestApi`

When tests are already written with JVM Libraries like JUnit and Google's Truth library, it would be a lot of work to migrate those tests over to `commonTest`, so these tests will only run on the JVM with KMP4FREE.  Additionally, this task alias means that your scripts that run Gradle Tasks do not have to update either when the plugin is enabled, since the `test` task will be available.

#### Task Aliasing
* `:module:test` ➡️ `:module:jvmTest`


## Multiplatform Disabled (Uses Kotlin JVM Plugin)

#### SourceSet Mapping
* `src/commonMain` ➡️ `src/main`
* `src/commonTest` ➡️ `src/test`
* `src/jvmTest` ➡️ `src/test`


#### Configuration Mapping
* `commonMainImplementation` ➡️ `implementation`
* `commonMainApi` ➡️ `api`
* `commonTestImplementation` ➡️ `testImplementation`
* `commonTestApi` ➡️ `testApi`
* `jvmTestImplementation` ➡️ `testImplementation`
* `jvmTestApi` ➡️ `testApi`

You can set the following properties in your `gradle.properties`


## FAQ
**What Configurations are Mapped?**
* api
* implementation
* compileOnly
* runtimeOnly
* apiDependenciesMetadata
* implementationDependenciesMetadata
* compileOnlyDependenciesMetadata
* runtimeOnlyDependenciesMetadata

**Can I use KMP4FREE on an Android Library?**

No.  KMP4FREE is set up to work for Kotlin JVM Projects.  However, a lot of code in Android Libraries are probably not Android Specific, so you can extract that into another module, or if possible, you can change the module from an Android Library to a Kotlin JVM project.

A Kotlin JVM Module means you cannot have resources, an `AndroidManifest.xml` and not use Android APIs.  You can always create your own abstractions to bridge between different modules.

Many projects already have Kotlin JVM projects, while not requiring the use of the Kotlin Multiplatform Plugin.
