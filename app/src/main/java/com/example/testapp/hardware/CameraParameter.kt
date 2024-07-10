package com.example.testapp.hardware

import android.content.Context
import android.graphics.ImageFormat
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager

object CameraParameter {

    fun get(context: Context): String {
        return try{
            val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            val cameraId = cameraManager.cameraIdList[0]
            val characteristics = cameraManager.getCameraCharacteristics(cameraId)
            val streamConfigurationMap =
                characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
            val sizes = streamConfigurationMap!!.getOutputSizes(ImageFormat.JPEG)
            val widthCamera = sizes[0].width
            val heightCamera = sizes[0].height
            val mpCamera = (widthCamera * heightCamera) / 1_000_000.0
            "Camera resolution: " + mpCamera + "mp(" + widthCamera + "x" + heightCamera + ")"
        }catch (e: Exception){
            "Camera is unavailable"
        }
    }
}