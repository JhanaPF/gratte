plugins {
    alias(libs.plugins.android.librairy)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.junit5)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.domain"
    compileSdk = 35

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":Common"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.gpuimage)

    testImplementation(platform(libs.test.junit5Bom))
    testImplementation(libs.junit)
    testImplementation(libs.test.junit5JupiterApi)
    testImplementation(libs.test.junit5JupiterParams)
    testRuntimeOnly(libs.test.junit5JupiterJupiterEngine)

    testImplementation(libs.test.turbine)
    testImplementation(libs.test.truth)
    testImplementation(libs.test.coroutines)
    testImplementation(libs.test.kotlinJunit)
    testImplementation(libs.test.mockk)
}

kapt {
    correctErrorTypes = true
}