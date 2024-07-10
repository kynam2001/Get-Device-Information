package com.example.testapp.hardware

import android.os.Build

object AndroidVersion {
    fun get(): String {
        val androidVersion = Build.VERSION.RELEASE
        val androidAPI = Build.VERSION.SDK_INT
        return "Android version: $androidVersion\nAPI: $androidAPI"
    }
}