package com.example.testapp.hardware

import android.os.Build

object AndroidModel {
    fun get(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        val codename = Build.DEVICE
        return "Model: $model\nManufacturer: $manufacturer\nCodename: $codename"
    }
}