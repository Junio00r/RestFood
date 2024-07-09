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
        minSdk = 30
        targetSdk = 34
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
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_18.toString()
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
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.7")
    val roomVersion = "2.6.1"
    val uiVersion = "1.6.7"

    // Compose
    implementation("androidx.compose.ui:ui:$uiVersion")
    implementation("androidx.compose.ui:ui-graphics:$uiVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$uiVersion")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))

    androidTestImplementation(platform("androidx.compose:compose-bom:2024.05.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$uiVersion")

    debugImplementation("androidx.compose.ui:ui-tooling:$uiVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$uiVersion")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("net.yslibrary.keyboardvisibilityevent:keyboardvisibilityevent:3.0.0-RC2")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")

    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$roomVersion")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")

    // optional - RxJava2 support for Room
    implementation("androidx.room:room-rxjava2:$roomVersion")

    // optional - RxJava3 support for Room
    implementation("androidx.room:room-rxjava3:$roomVersion")

    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:$roomVersion")

    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$roomVersion")

    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:$roomVersion")

    // Dependencias para implementar arquitetura MVVM
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")


    // Retrofit - Biblioteca de requisições REST
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

    // Espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Junit
    testImplementation("junit:junit:4.13.2")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")
}
