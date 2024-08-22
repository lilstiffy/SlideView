import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
}

val properties = Properties()
properties.load(project.rootProject.file("publishing.properties").inputStream())
val githubUser: String = properties.getProperty("GITHUB_USER") ?: ""
val githubToken: String = properties.getProperty("GITHUB_TOKEN") ?: ""

android {
    namespace = "com.lilstiffy.slideview"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.lilstiffy"
            artifactId = "slideview"
            version = "1.0.0"
            artifact("$buildDir/outputs/aar/SlideView-release.aar")
        }
    }

    repositories {
        maven {
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/lilstiffy/SlideView")
            credentials {
                username = githubUser
                password = githubToken
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
}