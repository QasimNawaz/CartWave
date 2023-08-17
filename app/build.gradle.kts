import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
//    alias(libs.plugins.kotlin.parcelize)
    id("kotlin-parcelize")
//    alias(libs.plugins.kotlin.kapt)
    id("kotlin-kapt")
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.gms.google.services)
}

android {
    namespace = "com.qasimnawaz019.cartwave"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.qasimnawaz019.cartwave"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(libs.bundles.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.testing)

    implementation(libs.compose.bom)
    androidTestImplementation(libs.compose.bom)

    implementation(libs.bundles.compose.common.impl)
    debugImplementation(libs.bundles.compose.common.debug.impl)
    androidTestImplementation(libs.compose.junit4)

    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)

    implementation(libs.bundles.paging)

    implementation(libs.coil)

    implementation(libs.gson)

    implementation(libs.bundles.room)
    kapt(libs.room.compiler)

    implementation(platform(libs.firebase.bom))
}