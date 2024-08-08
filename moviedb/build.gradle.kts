plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.op.moviedb"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

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
            buildConfigField("String", "MOVIE_DB_BASE_URL", "\"https://api.themoviedb.org\"")
            buildConfigField("String", "MOVIE_DB_API_KEY", "\"${property("MOVIE_DB_API_KEY")}\"")
            buildConfigField("String", "MOVIE_DB_IMAGE_BASE_URL", "\"https://image.tmdb.org/t/p/w500\"")
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "MOVIE_DB_BASE_URL", "\"https://api.themoviedb.org\"")
            buildConfigField("String", "MOVIE_DB_API_KEY", "\"${property("MOVIE_DB_API_KEY")}\"")
            buildConfigField("String", "MOVIE_DB_IMAGE_BASE_URL", "\"https://image.tmdb.org/t/p/w500\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    kapt {
        correctErrorTypes = true
    }
    buildFeatures.buildConfig = true
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    // Retrofit
    implementation(libs.squareup.retrofit2)
    implementation(libs.google.code.gson)
    implementation(libs.squareup.retrofit2.converter.gson)

    //OkHttp3
    implementation(libs.squareup.okhttp3)
}