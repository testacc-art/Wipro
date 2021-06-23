plugins {
    kotlin("jvm") version "1.4.32"
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

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}


tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}