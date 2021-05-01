package com.example.taxi_app.fragments.screens

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.taxi_app.R
import com.example.taxi_app.databinding.FragmentMapsBinding
import com.example.taxi_app.utilites.APP_ACTIVITY
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mikepenz.iconics.Iconics.applicationContext

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MapsFragment : Fragment(R.layout.fragment_maps), OnMapReadyCallback {

    private lateinit var binding: FragmentMapsBinding
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapsBinding.bind(view)
    }

    override fun onStart() {
        super.onStart()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(APP_ACTIVITY)
        fetchLocation()
    }

    //Fetch user location
    private fun fetchLocation(){
        if (ActivityCompat.checkSelfPermission(
                APP_ACTIVITY, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                APP_ACTIVITY, Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions( APP_ACTIVITY,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return

        }
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if(location != null){
                currentLocation = location
                Toast.makeText(applicationContext, currentLocation.latitude.toString() + "" +
                        currentLocation.longitude, Toast.LENGTH_LONG).show()
                val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                mapFragment?.getMapAsync(this)
            }
        }
    }

    //Add tracking
    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val markerOptions = MarkerOptions().position(latLng).title("Я тут!")
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap.addMarker(markerOptions)
    }

    //Permission
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        when(requestCode){
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                fetchLocation()
            }
        }
    }
}