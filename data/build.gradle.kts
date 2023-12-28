import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("kotlinx-serialization")
//    alias(libs.plugins.ksp)
}

fun getLocalProperty(propertyName: String): String {
    val properties = Properties().apply {
        load(FileInputStream(rootProject.file("local.properties")))
    }
    return properties.getProperty(propertyName, "")
}

android {
    namespace = "com.qasimnawaz019.data"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"${getLocalProperty("baseUrl")}\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            buildConfigField("String", "BASE_URL", "\"${getLocalProperty("baseUrl")}\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        buildConfig = true
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
    implementation(project(":domain"))
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

    api(libs.bundles.dataStore)

//    api(libs.bundles.room)
//    ksp(libs.room.compiler)
}