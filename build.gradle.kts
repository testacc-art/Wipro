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

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    plugins.apply("org.jetbrains.dokka")

    print("dokkaHtmlVikram 0")

    tasks.named<org.jetbrains.dokka.gradle.DokkaTask>("dokkaHtml").configure {
        print("dokkaHtmlVikram 1")
        dokkaSourceSets {
            named("main") {
                print("dokkaHtmlVikram 2")
                noAndroidSdkLink.set(false)
                suppressInheritedMembers.set(true)
            }
        }
    }

    tasks.named<org.jetbrains.dokka.gradle.DokkaTask>("dokkaHtml") {
        print("dokkaHtmlVikram 11")
        dokkaSourceSets {
            named("main") {
                print("dokkaHtmlVikram 12")
                noAndroidSdkLink.set(true)
                suppressInheritedMembers.set(true)
            }
        }
    }


    tasks.dokkaHtml.configure {
        print("dokkaHtmlVikram 51")

        dokkaSourceSets {
            named("main") { /* configure main source set */
                print("dokkaHtmlVikram 52")
            }
            configureEach {  /* configure all source sets */
                print("dokkaHtmlVikram 53")
            }
            register("custom") { /* register custom source set */
                print("dokkaHtmlVikram 54")
            }
        }
    }
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    suppressInheritedMembers.set(true)
    print("dokkaHtmlVikram 21")
    dokkaSourceSets {
        named("main") {
            print("dokkaHtmlVikram 22")
            noAndroidSdkLink.set(true)
            suppressInheritedMembers.set(true)
        }
    }
}

tasks.dokkaHtml.configure {
    print("dokkaHtmlVikram 31")

    dokkaSourceSets {
        named("main") { /* configure main source set */
            print("dokkaHtmlVikram 32")
        }
        configureEach {  /* configure all source sets */
            print("dokkaHtmlVikram 33")
        }
        register("custom") { /* register custom source set */
            print("dokkaHtmlVikram 34")
        }
    }
}

tasks.dokkaHtmlMultiModule.configure {
    print("dokkaHtmlVikram 41")
    suppressInheritedMembers.set(false)
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    print("dokkaHtmlVikram 71")
    dokkaSourceSets {
        print("dokkaHtmlVikram 72")
        named("main") {
            print("dokkaHtmlVikram 73")
            sourceLink {
                print("dokkaHtmlVikram 74")
            }
        }
    }
}