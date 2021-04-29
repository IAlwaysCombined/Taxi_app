package com.example.taxi_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taxi_app.databinding.ActivityMainBinding
import com.example.taxi_app.fragments.MapsFragment
import com.example.taxi_app.utilites.replaceFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFields()
    }

    private fun initFields(){
        replaceFragment(MapsFragment())
    }
}