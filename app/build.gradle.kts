    plugins {
        alias(libs.plugins.android.application)
        alias(libs.plugins.google.gms.google.services)
    }

    android {
        namespace = "com.example.dialektogo"
        compileSdk = 35

        defaultConfig {
            applicationId = "com.example.dialektogo"
            minSdk = 24
            targetSdk = 34
            versionCode = 1
            versionName = "1.0"

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }

    dependencies {

        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.activity)
        implementation(libs.constraintlayout)
        implementation(libs.firebase.inappmessaging)
        implementation(libs.firebase.auth)
        implementation(libs.firebase.firestore)
        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")

    }