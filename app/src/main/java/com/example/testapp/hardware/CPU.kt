package com.example.testapp.hardware

import java.io.IOException
import java.io.RandomAccessFile

object CPU {

    fun get(): String{
        var minCPUFreq = getMinCPUFreq(0)
        var maxCPUFreq = getMaxCPUFreq(0)
        for (i in 0 until getCPUCores()){
            if (minCPUFreq > getMinCPUFreq(i)){
                minCPUFreq = getMinCPUFreq(i)
            }
            if (maxCPUFreq < getMaxCPUFreq(i)){
                maxCPUFreq = getMaxCPUFreq(i)
            }
        }
        return "CPU core: " + getCPUCores() + " cores\n" + "CPU: "+ minCPUFreq + " - " + maxCPUFreq + " MHz"
    }

    private fun getMinCPUFreq(core: Int): Int {
        var minFreq = 0
        try {
            val randomAccessFile =
                RandomAccessFile("/sys/devices/system/cpu/cpu$core/cpufreq/cpuinfo_min_freq", "r")
            while (true) {
                val line = randomAccessFile.readLine() ?: break
                val timeInState = line.toInt()
                if (timeInState > 0) {
                    val freq = timeInState / 1000
                    if (freq > minFreq) {
                        minFreq = freq
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return minFreq
    }

    private fun getMaxCPUFreq(core: Int): Int {
        var maxFreq = 0
        try {
            val randomAccessFile =
                RandomAccessFile("/sys/devices/system/cpu/cpu$core/cpufreq/cpuinfo_max_freq", "r")
            while (true) {
                val line = randomAccessFile.readLine() ?: break
                val timeInState = line.toInt()
                if (timeInState > 0) {
                    val freq = timeInState / 1000
                    if (freq > maxFreq) {
                        maxFreq = freq
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return maxFreq
    }

    private fun getCPUCores(): Int {
        return Runtime.getRuntime().availableProcessors()
    }
}