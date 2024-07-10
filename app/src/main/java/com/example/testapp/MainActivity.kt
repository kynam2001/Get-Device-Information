package com.example.testapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.testapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showScreen()
    }

    private fun showScreen(){
        val fragmentShowDeviceInfo = FragmentShowDeviceInfo()
        replaceShowFragment(fragmentShowDeviceInfo)
    }

    private fun replaceShowFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.helloFragment, fragment).addToBackStack(null)
        transaction.commit()
    }

}