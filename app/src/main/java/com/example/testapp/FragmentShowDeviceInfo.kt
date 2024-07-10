package com.example.testapp

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.opengl.GLES32
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.testapp.databinding.ShowDeviceInfoFragmentBinding
import com.example.testapp.hardware.AndroidModel
import com.example.testapp.hardware.AndroidVersion
import com.example.testapp.hardware.CPU
import com.example.testapp.hardware.CameraParameter
import com.example.testapp.hardware.Memory
import com.example.testapp.hardware.Permission
import com.example.testapp.hardware.RAM
import com.example.testapp.hardware.ScreenParameter
import java.io.File
import java.io.FileOutputStream
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class FragmentShowDeviceInfo : Fragment(), GLSurfaceView.Renderer {

    private lateinit var binding: ShowDeviceInfoFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ShowDeviceInfoFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        showDeviceInfo()
        return view
    }

    private fun showDeviceInfo(){
        binding.surfaceView.setRenderer(this)
        binding.txtViewModel.text = AndroidModel.get()
        binding.txtViewAndroidVersion.text = AndroidVersion.get()
        binding.txtViewRAM.text = RAM.get(context as MainActivity)
        binding.txtViewMemory.text = Memory.get()
        binding.txtViewCPU.text =  CPU.get()
        binding.txtViewScreen.text = ScreenParameter.get(context as MainActivity)
        binding.txtViewCamera.text = CameraParameter.get(context as MainActivity)
        binding.txtViewCheckRoot.text = Permission.isRooted()
        binding.txtViewCheckBLE.text = Permission.checkBLE(context as MainActivity)
        binding.btnExportTxt.setOnClickListener {
            writeToFile(writeToFileHelper(), context as MainActivity)
            Toast.makeText(context,"Location: " + context?.getExternalFilesDir(null).toString(), Toast.LENGTH_LONG).show()
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, writeToFileHelper())
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun writeToFileHelper(): String{
        return  "#Device\n" + binding.txtViewModel.text.toString() + "\n" +
                "#Android\n" + binding.txtViewAndroidVersion.text.toString() + "\n" +
                "#RAM\n" + binding.txtViewRAM.text.toString() + "\n" +
                "#Memory\n" + binding.txtViewMemory.text.toString() + "\n" +
                "#CPU\n" + binding.txtViewCPU.text.toString() + "\n" +
                "#GPU\n" + binding.txtViewGPU.text.toString() + "\n" +
                "#Screen\n" + binding.txtViewScreen.text.toString() + "\n" +
                "#Camera\n" + binding.txtViewCamera.text.toString() + "\n" +
                "#Root\n" + binding.txtViewCheckRoot.text.toString() + "\n" +
                "#BLE\n" + binding.txtViewCheckBLE.text.toString()
    }

    private fun writeToFile(data: String, context: Context) {
        val path = context.getExternalFilesDir(null)
        val file = File(path, "output.txt")
        val stream = FileOutputStream(file)
        stream.use { stream ->
            stream.write(data.toByteArray())
        }
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig?) {
        val activityManager = context?.getSystemService(AppCompatActivity.ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo
        val stringGPU = "GPU vendor: " + gl.glGetString(GLES32.GL_VENDOR) +
                "\nGPU renderer: " + gl.glGetString(GLES32.GL_RENDERER) +
                "\nOpenGL version: " + configurationInfo.glEsVersion
        binding.txtViewGPU.post{  binding.txtViewGPU.text = stringGPU}
        binding.surfaceView.post{ binding.surfaceView.visibility = View.GONE }
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {}

    override fun onDrawFrame(gl: GL10) {}
}