package com.example.testapp.hardware

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
import java.io.File

object Permission {
    fun isRooted(): String{
        val su = "su"
        val locations = arrayOf(
            "/sbin/",
            "/system/bin/",
            "/system/xbin/",
            "/system/sd/xbin/",
            "/system/bin/failsafe/",
            "/data/local/xbin/",
            "/data/local/bin/",
            "/data/local/"
        )
        val stringRoot: String
        for (location in locations) {
            if (File(location + su).exists()) {
                stringRoot = "Rooted: "+ true
                return stringRoot
            }
        }
        stringRoot = "Rooted: " + false
        return stringRoot
    }

    fun checkBLE(context: Context): String{
        val stringBLE: String
        if (!context.packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            stringBLE = "BLE supported: "+ false + checkMultipleAdvertisement()
            return stringBLE
        }
        stringBLE = "BLE supported: "+ true + checkMultipleAdvertisement()
        return stringBLE
    }

    private fun checkMultipleAdvertisement(): String{
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(bluetoothAdapter!= null && bluetoothAdapter.isMultipleAdvertisementSupported){
            return "\nMultiple advertisement supported: " + true
        }
        return "\nMultiple advertisement supported: "+ false
    }
}