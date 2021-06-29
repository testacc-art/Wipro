plugins {
    kotlin(Libs.Plugins.kotlinJVM) version Libs.Versions.kotlin

    id(Libs.Plugins.dokka) version (Libs.Versions.dokka)
    id(Libs.Plugins.spotless) version Libs.Versions.spotless

    id(Libs.Plugins.detekt) version Libs.Versions.detekt

}

buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Libs.androidGradlePlugin)
        classpath(Libs.Kotlin.gradlePlugin)
        classpath(Libs.AndroidX.Navigation.navigationPlugin)
        classpath(Libs.DaggerHilt.classPath)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {

    plugins.apply(Libs.Plugins.spotless)

    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")

            ktlint(Libs.Versions.ktlint)
            licenseHeaderFile("${project.rootProject.projectDir}/config/spotless/copyright.kt")
        }

        kotlinGradle {
            target("*.gradle.kts")
            ktlint()
        }
    }

    plugins.apply(Libs.Plugins.dokka)

    tasks.named<org.jetbrains.dokka.gradle.DokkaTaskPartial>("dokkaHtmlPartial") {
        dokkaSourceSets.configureEach {
            noAndroidSdkLink.set(true)
            suppressInheritedMembers.set(true)
        }
    }

    plugins.apply(Libs.Plugins.detekt)

    detekt {
        config = rootProject.files("${rootProject.projectDir}/config/detekt/detekt.yml")
        baseline = file("${rootProject.projectDir}/config/detekt/baseline.xml")
        reports {
            html {
                enabled = true
                destination = file("$rootDir/reports/detekt/detekt.html")
            }
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            // Treat all Kotlin warnings as errors
            allWarningsAsErrors = true
            freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")

            // Enable experimental coroutines APIs, including Flow
            freeCompilerArgs = listOf("-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
            freeCompilerArgs = listOf("-Xopt-in=kotlinx.coroutines.FlowPreview")
            freeCompilerArgs = listOf("-Xopt-in=kotlin.Experimental")

            // Set JVM target to 1.8
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
}

val detektProjectBaseline by tasks.registering(io.gitlab.arturbosch.detekt.DetektCreateBaselineTask::class) {
    description = "Overrides current baseline."
    buildUponDefaultConfig.set(true)
    ignoreFailures.set(false)
    parallel.set(true)
    setSource(files(rootDir))
    config.setFrom(file("$rootDir/config/detekt/detekt.yml"))
    baseline.set(file("$rootDir/config/detekt/baseline.xml"))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
}


/*Report Generation*/
tasks.withType<org.jetbrains.dokka.gradle.DokkaMultiModuleTask>().configureEach {
    outputDirectory.set(file("$rootDir/reports/dokka"))
}


dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.17.1")
}