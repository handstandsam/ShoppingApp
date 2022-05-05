import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

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

        val implementation by configurations.creating
        val testImplementation by configurations.creating

        configurations.getByName(commonMain.implementationConfigurationName).extendsFrom(implementation)
        configurations.getByName(commonTest.implementationConfigurationName).extendsFrom(testImplementation)

        fun createAliasConfigurations(sourceSet: KotlinSourceSet) {
            sourceSet.relatedConfigurationNames.forEach { configurationName ->
                val aliasName = configurationName
                    .removePrefix("common")
                    .removePrefix("Main")
                    .decapitalize()

                val configuration = configurations.getByName(configurationName)
                val aliasConfiguration = configurations.maybeCreate(aliasName)
                configuration.extendsFrom(aliasConfiguration)
            }
        }

        createAliasConfigurations(commonMain)
        createAliasConfigurations(commonTest)
    }

    println(configurations.map { it.name }.sorted())
}
