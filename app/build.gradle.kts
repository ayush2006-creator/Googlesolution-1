plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.sample_front_screen"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.sample_front_screen"
        minSdk = 24
        //noinspection EditedTargetSdkVersion
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}
val ktor_version: String by project
dependencies {
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation ("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.compose.material:material-icons-extended:1.6.1")
    implementation("io.ktor:ktor-client-core:2.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // SLF4J Simple Binding (for logging)
    implementation("org.slf4j:slf4j-simple:2.0.7")

        // Agora RTC SDK
    implementation( "io.agora.rtc:full-sdk:3.7.2")




    // Ktor OkHttp engine (for Android)
    implementation("io.ktor:ktor-client-okhttp:2.3.5")
    // Ktor Content Negotiation (for JSON serialization)
    implementation("io.ktor:ktor-client-content-negotiation:2.3.5")
    // Ktor Gson serializer
    implementation("io.ktor:ktor-serialization-gson:2.3.5")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}