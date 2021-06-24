plugins {
    kotlin("jvm") version "1.5.10"
    id("org.jetbrains.dokka") version ("1.4.32")
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

plugins {
    id("com.diffplug.spotless") version "5.14.0"
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {

    apply(plugin = "com.diffplug.spotless")

    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target ("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")

            ktlint("0.40.0")
            licenseHeaderFile("${project.rootProject.projectDir}/spotless/copyright.kt")
        }

        kotlinGradle {
            target("*.gradle.kts")
            ktlint()
        }

        format ("xml") {
            target ("**/*.xml")
        }
    }
}