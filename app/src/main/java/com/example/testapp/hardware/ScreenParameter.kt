package com.example.testapp.hardware

import android.app.Activity
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.Display
import java.util.Locale
import kotlin.math.pow
import kotlin.math.sqrt

object ScreenParameter {
    fun get(context: Activity): String {
        val displayMetrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getRealMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        return "Screen resolution: " + width.toString() + "x" + height.toString() +
                "\nScreen size: " + getScreenSize(context)
    }

    private fun getScreenSize(context: Activity): String {
        var widthPixels = 0
        var heightPixels = 0
        val windowManager = context.windowManager
        val display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)
        try {
            val realSize = Point()
            Display::class.java.getMethod("getRealSize", Point::class.java)
                .invoke(display, realSize)
            widthPixels = realSize.x
            heightPixels = realSize.y
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val x = (widthPixels / displayMetrics.xdpi).toDouble().pow(2.0)
        val y = (heightPixels / displayMetrics.ydpi).toDouble().pow(2.0)
        val screenSize = sqrt(x + y)
        return String.format(Locale.ENGLISH, "%.2f", screenSize) + "\""
    }
}