plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("kotlinx-serialization")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.qasimnawaz019.domain"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    api(libs.bundles.common)
    api(libs.junit)
    androidTestApi(libs.bundles.testing)

    api(libs.bundles.koin)
    testApi(libs.koin.test)

    api(libs.bundles.coroutines)

    api(libs.gson)
    api(libs.bundles.ktor)

    api(libs.bundles.paging)

    api(libs.bundles.room)
    ksp(libs.room.compiler)
}