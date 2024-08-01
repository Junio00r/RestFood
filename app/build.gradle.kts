plugins {

    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
}

android {
    namespace = "com.devmobile.android.restaurant"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.devmobile.android.restaurant"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }

        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
//    buildFeatures {
//        compose = true
//    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.10"
//    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    /* ------------------------------------------------------------------------------- *
     *                     // Application Dependencies (APK) //                        *
     * ------------------------------------------------------------------------------- */

    // Compose
    implementation("androidx.compose.ui:ui:1.6.8")
    implementation("androidx.compose.ui:ui-graphics:1.6.8")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.8")
    implementation("androidx.activity:activity-compose:1.9.1")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.8")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.8")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.12.0")
    implementation("net.yslibrary.keyboardvisibilityevent:keyboardvisibilityevent:3.0.0-RC2")

    // Room
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-runtime:2.6.1")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.6.1")
    // optional - RxJava2 support for Room
    implementation("androidx.room:room-rxjava2:2.6.1")
    // optional - RxJava3 support for Room
    implementation("androidx.room:room-rxjava3:2.6.1")
    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:2.6.1")
    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:2.6.1")

    // Dependencies to implements MVVM architecture
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")


    /* ------------------------------------------------------------------------------- *
     *                         // Implementation Tests //                              *
     * ------------------------------------------------------------------------------- */

    // others
    androidTestImplementation("androidx.test:core:1.6.1")
    androidTestImplementation("androidx.test:rules:1.6.1")
    androidTestImplementation("androidx.test:runner:1.6.1")
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.3.0")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.06.00"))

    // Junit
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.8")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.2.1")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")

    // Espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")


    /* ------------------------------------------------------------------------------- *
     *                              // Unit Tests //                                   *
     * ------------------------------------------------------------------------------- */

    // others
    testImplementation("androidx.test:core:1.6.1")
    testImplementation("androidx.test:core-ktx:1.6.1")
    testImplementation("androidx.test:monitor:1.7.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.0")
    testImplementation("org.robolectric:robolectric:4.2.1")

    // Room
    testImplementation("androidx.room:room-testing:2.6.1")

    // Mockito - Mock
    testImplementation("org.mockito:mockito-core:5.12.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
    testImplementation("io.mockk:mockk:1.13.12")

    // Junit
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.3")
    androidTestUtil("androidx.test:orchestrator:1.5.0")

}
