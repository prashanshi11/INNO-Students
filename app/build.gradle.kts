plugins {
    id("com.android.application")         // Android app plugin
    id("org.jetbrains.kotlin.android")    // Kotlin for Android
    id("org.jetbrains.kotlin.kapt")       // Kotlin Annotation Processing Tool (used by Room)
    id("com.google.gms.google-services")  // Firebase services (e.g., Auth, Firestore)
    id("com.google.firebase.crashlytics") // Firebase Crashlytics (optional, for crash reporting)
    id("kotlin-parcelize")                // Kotlin Parcelable support using @Parcelize
}


android {
    namespace = "com.example.innostudent"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.innostudent"
        minSdk = 23
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true // Enable ViewBinding
        dataBinding = true // Enable DataBinding
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
}

kapt {
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.23")
    kapt("org.jetbrains.kotlin:kotlin-annotation-processing:1.9.23")

    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))

    // Compose Core
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.compose.runtime:runtime")
    implementation("androidx.compose.runtime:runtime-livedata")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // UI Core
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.6.0")
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Layout
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")

    // Desugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

    // Firebase (via BOM)
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-database-ktx:20.1.0")


    // Google Sign-In
    implementation("com.google.android.gms:play-services-auth:21.0.0")

    // AndroidX Credentials (for Sign-In with Google One Tap etc.)
    implementation("androidx.credentials:credentials:1.0.0-alpha02")
    implementation("androidx.credentials:credentials-play-services-auth:1.0.0-alpha02")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Additional Dependencies for Fragment and ViewModel support
    implementation("androidx.fragment:fragment-ktx:1.5.5")  // Latest version
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")  // Latest version



}
