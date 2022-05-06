/**
 * The goal of this convention plugin is to support switching out from kotlin("jvm") without any other changes.
 */

plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()
    sourceSets {
        val commonMain by getting {
            kotlin.srcDirs("src/main/java", "src/main/kotlin")
        }

        val commonTest by getting {
            kotlin.srcDirs("src/test/java", "src/test/kotlin")
        }

        val api by configurations.creating
        val implementation by configurations.creating
        val compileOnly by configurations.creating
        val runtimeOnly by configurations.creating
        val apiDependenciesMetadata by configurations.creating
        val implementationDependenciesMetadata by configurations.creating
        val compileOnlyDependenciesMetadata by configurations.creating
        val runtimeOnlyDependenciesMetadata by configurations.creating

        configurations.getByName(commonMain.apiConfigurationName)
            .extendsFrom(api)
        configurations.getByName(commonMain.implementationConfigurationName)
            .extendsFrom(implementation)
        configurations.getByName(commonMain.compileOnlyConfigurationName)
            .extendsFrom(compileOnly)
        configurations.getByName(commonMain.runtimeOnlyConfigurationName)
            .extendsFrom(runtimeOnly)
        configurations.getByName(commonMain.apiMetadataConfigurationName)
            .extendsFrom(apiDependenciesMetadata)
        configurations.getByName(commonMain.implementationMetadataConfigurationName)
            .extendsFrom(implementationDependenciesMetadata)
        configurations.getByName(commonMain.compileOnlyMetadataConfigurationName)
            .extendsFrom(compileOnlyDependenciesMetadata)
        configurations.getByName(commonMain.runtimeOnlyMetadataConfigurationName)
            .extendsFrom(runtimeOnlyDependenciesMetadata)

        val testApi by configurations.creating
        val testImplementation by configurations.creating
        val testCompileOnly by configurations.creating
        val testRuntimeOnly by configurations.creating
        val testApiDependenciesMetadata by configurations.creating
        val testImplementationDependenciesMetadata by configurations.creating
        val testCompileOnlyDependenciesMetadata by configurations.creating
        val testRuntimeOnlyDependenciesMetadata by configurations.creating

        configurations.getByName(commonTest.apiConfigurationName)
            .extendsFrom(testApi)
        configurations.getByName(commonTest.implementationConfigurationName)
            .extendsFrom(testImplementation)
        configurations.getByName(commonTest.compileOnlyConfigurationName)
            .extendsFrom(testCompileOnly)
        configurations.getByName(commonTest.runtimeOnlyConfigurationName)
            .extendsFrom(testRuntimeOnly)
        configurations.getByName(commonTest.apiMetadataConfigurationName)
            .extendsFrom(testApiDependenciesMetadata)
        configurations.getByName(commonTest.implementationMetadataConfigurationName)
            .extendsFrom(testImplementationDependenciesMetadata)
        configurations.getByName(commonTest.compileOnlyMetadataConfigurationName)
            .extendsFrom(testCompileOnlyDependenciesMetadata)
        configurations.getByName(commonTest.runtimeOnlyMetadataConfigurationName)
            .extendsFrom(testRuntimeOnlyDependenciesMetadata)
    }
}
