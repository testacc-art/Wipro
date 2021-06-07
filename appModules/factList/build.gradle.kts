plugins {
    id(Libs.Plugins.androidLibrary)
    kotlin(Libs.Plugins.kotlinAndroid)
    kotlin(Libs.Plugins.kotlinKapt)
    id(Libs.Plugins.kotlinNavigation)
    id(Libs.Plugins.kaptDagger)
}

android {
    compileSdkVersion(AndroidSdk.compile)

    defaultConfig {
        minSdkVersion(AndroidSdk.min)
        multiDexEnabled = true

        consumerProguardFiles(
            file("proguard-rules.pro")
        )

        resConfigs(AndroidSdk.locales)

        //testInstrumentationRunner = Libs.TestDependencies.testRunner
        testInstrumentationRunner = "reprator.wipro.factlist.FactListHiltTestRunner"

        // The following argument makes the Android Test Orchestrator run its
        // "pm clear" command after each test invocation. This command ensures
        // that the app's state is completely cleared between tests.
        testInstrumentationRunnerArguments += mapOf(
            "clearPackageData" to "true"
        )
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }

    buildFeatures.dataBinding = true
    buildFeatures.viewBinding = true

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        map { it.java.srcDirs("src/${it.name}/kotlin") }
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                file("proguard-rules.pro")
            )
        }
    }

    packagingOptions {
        exclude("META-INF/atomicfu.kotlin_module")
        pickFirst("META-INF/*")
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
        animationsDisabled = true
    }
}

dependencies {
    implementation(project(AppModules.moduleBaseJava))
    implementation(project(AppModules.moduleBaseAndroid))
    implementation(project(AppModules.moduleNavigation))

    implementation(Libs.AndroidX.cardView)
    implementation(Libs.swiperefresh)
    implementation(Libs.AndroidX.constraintlayout)

    implementation(Libs.AndroidX.Lifecycle.livedata)

    implementation(Libs.AndroidX.Navigation.fragmentKtx)

    implementation(Libs.AndroidX.Fragment.fragment)
    implementation(Libs.AndroidX.Fragment.fragmentKtx)

    implementation(Libs.TestDependencies.Espresso.idlingResource)
    implementation(Libs.TestDependencies.Espresso.idlingResourceSupport)

    //Hilt
    implementation(Libs.DaggerHilt.hilt)
    kapt(Libs.DaggerHilt.hiltCompilerAndroid)

    testImplementation(Libs.TestDependencies.AndroidXTest.truth)
    testImplementation(Libs.TestDependencies.core)
    testImplementation(Libs.OkHttp.mockWebServer)
    testImplementation(Libs.TestDependencies.jUnit)
    testImplementation(Libs.TestDependencies.AndroidXTest.rules)
    testImplementation(Libs.TestDependencies.AndroidXTest.runner)
    testImplementation(Libs.TestDependencies.Mockk.unitTest)

    debugImplementation(Libs.Coroutines.coroutineTest) {
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-debug")
    }

    debugImplementation(Libs.TestDependencies.fragmentTesting)

    //Hilt Android UI test
    androidTestImplementation(Libs.DaggerHilt.hiltAndroidTest)
    kaptAndroidTest(Libs.DaggerHilt.hiltCompilerAndroid)

    androidTestImplementation(Libs.TestDependencies.Espresso.core)
    androidTestImplementation(Libs.TestDependencies.Espresso.contrib)
    androidTestImplementation(Libs.TestDependencies.Espresso.intents)

    androidTestImplementation(Libs.TestDependencies.AndroidXTest.truth)
    androidTestImplementation(Libs.TestDependencies.Mockk.instrumentedTest)

    androidTestImplementation(Libs.TestDependencies.AndroidXTest.junit)
    androidTestImplementation(Libs.TestDependencies.AndroidXTest.runner)
    androidTestUtil(Libs.TestDependencies.AndroidXTest.orchestrator)
}