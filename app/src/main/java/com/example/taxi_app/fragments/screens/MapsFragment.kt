package com.example.taxi_app.fragments.screens

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.taxi_app.R
import com.example.taxi_app.databinding.FragmentMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mikepenz.iconics.Iconics.applicationContext
import java.util.jar.Manifest

class MapsFragment : Fragment(R.layout.fragment_maps), OnMapReadyCallback {

    private lateinit var binding: FragmentMapsBinding
    private val callback = OnMapReadyCallback { googleMap ->

        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    lateinit var currentLocation: Location
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapsBinding.bind(view)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
        fetchLocation()
    }

    private fun fetchLocation(){
        if(ActivityCompat.checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity!!,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if(location != null){
                currentLocation = location
                Toast.makeText(applicationContext, currentLocation.latitude.toString() + "" + currentLocation.latitude, Toast.LENGTH_SHORT).show()
                val supportMapFragment = (fragmentManager?.findFragmentById(R.id.map) as
                        SupportMapFragment?)!!
                supportMapFragment.getMapAsync(this)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
     val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
     val markOption = MarkerOptions().position(latLng)
     googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
     googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
     googleMap?.addMarker(markOption)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            permissionCode -> if(grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED){
                fetchLocation()
            }
        }
    }
}