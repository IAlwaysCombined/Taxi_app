package com.example.taxi_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.taxi_app.activity.AuthActivity
import com.example.taxi_app.databinding.ActivityMainBinding
import com.example.taxi_app.models.User
import com.example.taxi_app.ui.screens.MapsFragment
import com.example.taxi_app.ui.objects.AppDrawer
import com.example.taxi_app.utilites.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    lateinit var appDrawer: AppDrawer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP_ACTIVITY = this
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
        initUser()
        initFirebase()
    }

    //init Func
    private fun initFunc() {
        println("---------------->" + USER.username)
        if (AUTH.currentUser != null) {
            replaceFragment(MapsFragment())
        }
        else{
            replaceActivity(AuthActivity())
        }
    }

    //Initial Users
    private fun initUser() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
            .addListenerForSingleValueEvent(AppValueEventListener {
                USER = it.getValue(User::class.java) ?: User()
            })
    }
}