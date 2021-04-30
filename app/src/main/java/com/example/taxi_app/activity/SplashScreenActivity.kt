package com.example.taxi_app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.taxi_app.MainActivity
import com.example.taxi_app.R
import com.example.taxi_app.databinding.ActivitySplashScreenBinding
import com.example.taxi_app.utilites.replaceActivity

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private var TIME_OUT:Long = 3000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        delaySplashScreen()
    }

    private fun delaySplashScreen() {
        Handler().postDelayed({
            replaceActivity(MainActivity())
            finish()
        },TIME_OUT)
    }
}