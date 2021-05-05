package com.example.taxi_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.taxi_app.activity.AuthActivity
import com.example.taxi_app.databinding.ActivityMainBinding
import com.example.taxi_app.ui.screens.MapsFragment
import com.example.taxi_app.models.User
import com.example.taxi_app.ui.objects.AppDrawer
import com.example.taxi_app.utilites.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
        initUser{
            initFields()
            initFunc()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    //init Fields
    private fun initFields(){
        toolbar = binding.mainToolbar
        appDrawer = AppDrawer()
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    //init Func
    private fun initFunc() {
        setSupportActionBar(toolbar)
        if (AUTH.currentUser != null) {
            appDrawer.create()
            replaceFragment(MapsFragment())
        }
        else{
            replaceActivity(AuthActivity())
        }
    }
}