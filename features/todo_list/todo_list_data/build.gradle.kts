import com.google.common.base.Charsets
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties

val keyProperties = Properties()
val propertiesFile = File(rootDir, "app.properties")
if (propertiesFile.isFile) {
    InputStreamReader(
        FileInputStream(propertiesFile),
        Charsets.UTF_8
    ).use { reader ->
        keyProperties.load(reader)
    }
}

plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
}

android {
    namespace = "id.haeworks.todo_list_data"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"${keyProperties["baseUrl.dev"]}\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"${keyProperties["baseUrl.prod"]}\"")

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
    }
    buildFeatures {
        buildConfig = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.features.todoList.todoListDomain)
    implementation(libs.androidx.core.ktx)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    testImplementation(libs.junit)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}