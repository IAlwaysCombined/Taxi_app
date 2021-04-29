package com.example.taxi_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.taxi_app.databinding.ActivityMainBinding
import com.example.taxi_app.fragments.MapsFragment
import com.example.taxi_app.fragments.objects.AppDrawer
import com.example.taxi_app.utilites.APP_ACTIVITY
import com.example.taxi_app.utilites.replaceFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    private lateinit var appDrawer: AppDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP_ACTIVITY = this
        initFields()
    }

    private fun initFields(){
        //replaceFragment(MapsFragment())
        toolbar = binding.mainToolbarUsers
        appDrawer = AppDrawer(this, toolbar)
        setSupportActionBar(toolbar)
        appDrawer.create()
    }
}