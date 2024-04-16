plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")

    //needed for automatic JSON serialization/deserialization
    //id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
    //for room
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    // For navigaton
    //id("androidx.navigation.safeargs.kotlin")


}

android {
    namespace = "com.example.finaldraw"
    compileSdk = 34



    defaultConfig {
        applicationId = "com.example.finaldraw"
        minSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {
                cppFlags += ""
            }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-runtime-testing:2.7.0")
    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.compose.foundation:foundation-android:1.6.4")
    implementation("androidx.compose.material3:material3-android:1.2.1")

    // Firebase
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
//    implementation(libs.firebase.common.ktx)
//    implementation(libs.firebase.auth.ktx)
//    implementation(libs.firebase.firestore.ktx)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //to get livedata + viewmodel stuff
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.core:core-splashscreen:1.0.1")
    //Fragment stuff
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    // Color picker
    implementation ("com.github.yukuku:ambilwarna:2.0.1")
    // Testing
    testImplementation ("junit:junit:4.13.2")
    //testImplementation ("org.mockito:mockito-core:3.11.2")
    testImplementation ("org.mockito:mockito-inline:3.11.2")
    //testImplementation ("androidx.arch.core:core-testing:2.1.0")

    // Room Stuff
    implementation("androidx.room:room-common:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    //KTOR dependencies
    implementation("io.ktor:ktor-client-core:2.3.8")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.8")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.8")
    implementation("io.ktor:ktor-client-cio:2.3.8")
    implementation("io.ktor:ktor-client-android:2.3.8")
    //implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

    //Jetpack compose
//    implementation ("androidx.compose.ui:ui:latest_version")
//    implementation ("androidx.compose.material:material:latest_version")
//    implementation ("androidx.activity:activity-compose:latest_version")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.02.02"))
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.navigation:navigation-common-ktx:2.7.7")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    //implementation(libs.androidx.material3.android)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.02"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
