package com.example.taxi_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.taxi_app.activity.AuthActivity
import com.example.taxi_app.databinding.ActivityMainBinding
import com.example.taxi_app.fragments.objects.AppDrawer
import com.example.taxi_app.utilites.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    private lateinit var appDrawer: AppDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP_ACTIVITY = this
        //replaceFragment(MapsFragment())
    }

    //Start fun MainActivity
    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()
    }

    //init Fields
    private fun initFields(){
        toolbar = binding.mainToolbar
        appDrawer = AppDrawer(this, toolbar)
        setSupportActionBar(toolbar)
        appDrawer.create()
        initFirebase()
    }

    //init Func
    private fun initFunc() {
        if (AUTH.currentUser != null) {
            replaceActivity(MainActivity())
        }
        else{
            replaceActivity(AuthActivity())
        }
    }
}