/*
* Copyright (C) 2023 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("de.mannodermaus.android-junit5") version "1.12.0.0"
    id("io.github.orange-3.unit-test-architect")
//    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.unscramble"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.unscramble"
        minSdk = 24
        targetSdk = 35
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
//    buildFeatures {
//        compose = true
//    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


}

//configurations.all {
//    resolutionStrategy.dependencySubstitution {
//        substitute(module("org.utils:api"))
//            .using(project(":api")).because("we work with the unreleased development version")
//    }
//}

tasks.register("generateTests", io.github.orange3.unittestarchitect.TestCaseGenerator::class) {
    exclude = listOf()
    urls = emptyArray()
    sourceDirectoryList = listOf("")
    doFirst {
        android.applicationVariants.forEach { variant ->
//        println("variant.name:" + variant.name)
            if (variant.name == "debug") {
                val javaCompiledClasses =
                    variant.getJavaCompileProvider().get().destinationDirectory.getAsFile().get()
                        .toURI().toURL()
                val restDependencies =
                    variant.getCompileClasspath(null).files.map { it.toURI().toURL() }
                        .toTypedArray()
                urls = restDependencies + javaCompiledClasses
                sourceDirectoryList = listOf("src/main/java/com/example/unscramble/ui")
                exclude = listOf()
            }
        }
    }
}

dependencies {
//    implementation(platform("androidx.compose:compose-bom:2024.12.01"))
//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
//    implementation("androidx.compose.material3:material3")
//    implementation("androidx.compose.ui:ui")
//    implementation("androidx.compose.ui:ui-graphics")
//    implementation("androidx.compose.ui:ui-tooling-preview")
//    debugImplementation("androidx.compose.ui:ui-test-manifest")
//    debugImplementation("androidx.compose.ui:ui-tooling")
//    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.google.android.material:material:1.12.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.2.0")

    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0-alpha01")
    androidTestImplementation("androidx.test.ext:junit:1.3.0-alpha01")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
    testImplementation("org.junit.platform:junit-platform-runner:1.8.2")
}
