plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("kotlinx-serialization")
//    alias(libs.plugins.ksp)
}

android {
    namespace = "com.qasimnawaz019.domain"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    api(libs.bundles.common)

    api(libs.junit)

    testApi(libs.mockito.core)
    testApi(libs.turbine)
    testApi(libs.truth)

    api(libs.bundles.koin)
    testApi(libs.koin.test)

    api(libs.bundles.coroutines)
    testApi(libs.coroutines.test)

    api(libs.gson)
    api(libs.bundles.ktor)

    api(libs.bundles.paging)

//    api(libs.bundles.room)
//    ksp(libs.room.compiler)
}