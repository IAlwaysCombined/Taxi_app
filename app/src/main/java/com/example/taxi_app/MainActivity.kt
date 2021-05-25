package com.example.taxi_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.taxi_app.activity.AuthActivity
import com.example.taxi_app.database.*
import com.example.taxi_app.databinding.ActivityMainBinding
import com.example.taxi_app.ui.screens.MapsFragment
import com.example.taxi_app.ui.objects.AppDrawer
import com.example.taxi_app.utilites.*
import retrofit2.Retrofit


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var appDrawer: AppDrawer
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP_ACTIVITY = this
        initFirebase()
        initBecomeDriver()
        initUser{
            initFields()
            initFunc()
        }
    }

    //init Fields
    private fun initFields(){
        toolbar = binding.mainToolbar
        appDrawer = AppDrawer()
    }

    //init Func
    private fun initFunc() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        if (AUTH.currentUser != null) {
            hideKeyboard()
            appDrawer.create()
            replaceFragment(MapsFragment())
        }
        else{
            hideKeyboard()
            replaceActivity(AuthActivity())
        }
    }
}