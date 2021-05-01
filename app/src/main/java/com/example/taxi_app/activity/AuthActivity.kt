package com.example.taxi_app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taxi_app.R
import com.example.taxi_app.databinding.ActivityAuthBinding
import com.example.taxi_app.ui.screens.EnterPhoneNumberFragment
import com.example.taxi_app.utilites.replaceFragment

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        replaceFragment(EnterPhoneNumberFragment())
    }
}