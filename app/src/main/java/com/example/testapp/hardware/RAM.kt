package com.example.testapp.hardware

import android.app.ActivityManager
import android.content.Context

object RAM {
    fun get(context: Context): String{
        val mi = ActivityManager.MemoryInfo()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(mi)
        val availableMem: Long = mi.availMem / 0x100000L
        val totalMem: Long = mi.totalMem / 0x100000L
        return "Available Ram: " + availableMem.toString() + "MB\nTotal RAM: " + totalMem.toString() +"MB"
    }
}