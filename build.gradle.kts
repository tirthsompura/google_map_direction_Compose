// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.48.1")
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")

    }
}

plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.android.library") version "8.2.2" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
}