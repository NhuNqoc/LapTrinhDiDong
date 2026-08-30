plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.phonestore"

    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.phonestore"

        minSdk = 24
        targetSdk = 37

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    // ==========================================
    // ANDROID CƠ BẢN
    // ==========================================

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)


    // ==========================================
    // JETPACK COMPOSE
    // ==========================================

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)


    // ==========================================
    // MATERIAL ICONS
    // ==========================================

    implementation(
        "androidx.compose.material:material-icons-extended"
    )


    // ==========================================
    // FIREBASE
    // ==========================================

    implementation(
        platform(
            "com.google.firebase:firebase-bom:34.17.0"
        )
    )

    implementation(
        "com.google.firebase:firebase-auth"
    )

    implementation(
        "com.google.firebase:firebase-firestore"
    )


    // ==========================================
    // COIL - HIỂN THỊ ẢNH
    // ==========================================

    implementation(
        "io.coil-kt.coil3:coil-compose:3.3.0"
    )


    // ==========================================
    // UNIT TEST
    // ==========================================

    testImplementation(libs.junit)


    // ==========================================
    // ANDROID TEST
    // ==========================================

    androidTestImplementation(
        platform(libs.androidx.compose.bom)
    )

    androidTestImplementation(
        libs.androidx.compose.ui.test.junit4
    )

    androidTestImplementation(
        libs.androidx.espresso.core
    )

    androidTestImplementation(
        libs.androidx.junit
    )


    // ==========================================
    // DEBUG
    // ==========================================

    debugImplementation(
        libs.androidx.compose.ui.test.manifest
    )

    debugImplementation(
        libs.androidx.compose.ui.tooling
    )
}