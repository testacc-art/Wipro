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
        testInstrumentationRunner = "reprator.wipro.factlist.FactListTestRunner"
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

    /*
       Tests
    */
    // Hilt
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.36")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.36")

    debugImplementation("androidx.fragment:fragment-testing:1.3.4")

    androidTestImplementation("androidx.test:core-ktx:1.3.0")


    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    androidTestImplementation("io.mockk:mockk-android:1.10.5")
    androidTestImplementation("androidx.lifecycle:lifecycle-runtime-testing:2.3.1")

}