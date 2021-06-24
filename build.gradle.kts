// Top-level build file where you can add configuration options common to all sub-projects/modules.
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