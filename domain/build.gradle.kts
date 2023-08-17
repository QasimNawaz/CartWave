plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    //    alias(libs.plugins.kotlin.parcelize)
    id("kotlin-parcelize")
//    alias(libs.plugins.kotlin.kapt)
    id("kotlin-kapt")
    alias(libs.plugins.dagger.hilt.android)
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

    implementation(libs.bundles.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.testing)

    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)

    implementation(libs.bundles.coroutines)

    implementation(libs.gson)

    implementation(libs.bundles.paging)
    
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)
}