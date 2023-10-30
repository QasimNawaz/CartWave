plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
//    id("kotlin-parcelize")
    id("kotlinx-serialization")
//    id("kotlin-kapt")
    alias(libs.plugins.ksp)
//    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "com.qasimnawaz019.data"
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
    implementation(project(":domain"))
    implementation(libs.bundles.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.testing)
//    implementation(libs.kotlinx.serialization.json)

//    implementation(libs.bundles.hilt)
//    kapt(libs.hilt.android.compiler)
    implementation(libs.bundles.koin)
    testImplementation(libs.koin.test)

    implementation(libs.bundles.coroutines)

    implementation(libs.gson)

//    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.ktor)

    implementation(libs.bundles.paging)

    implementation(libs.bundles.dataStore)

    implementation(libs.bundles.room)
    ksp(libs.room.compiler)
}