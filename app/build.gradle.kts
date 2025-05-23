plugins {
    id("com.android.application")
    // If you're using Kotlin, uncomment the next line
    // id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.appprojet"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.appprojet"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
}

dependencies {
    // Material Components
    implementation("com.google.android.material:material:1.9.0")
    // MPAndroidChart for analytics
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
