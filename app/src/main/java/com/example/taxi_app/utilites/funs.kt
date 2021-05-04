package com.example.taxi_app.utilites

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.taxi_app.MainActivity
import com.example.taxi_app.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.SupportMapFragment

fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.replaceActivity(activity: Activity) {
    val intent = Intent(this, activity::class.java)
    startActivity(intent)
    this.finish()
}

fun Fragment.replaceActivity(activity: Activity) {
    val intent = Intent(this.context, activity::class.java)
    startActivity(intent)
}

fun AppCompatActivity.replaceFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .addToBackStack(null)
        .replace(R.id.main_container, fragment)
        .commit()
}

fun Fragment.replaceFragment(fragment: Fragment) {
    fragmentManager?.beginTransaction()
        ?.addToBackStack(null)
        ?.replace(R.id.main_container, fragment)
        ?.commit()
}

//AppCompat activity help method
fun restartActivity() {
    val intent = Intent(APP_ACTIVITY, MainActivity::class.java)
    APP_ACTIVITY.startActivity(intent)
    APP_ACTIVITY.finish()
}