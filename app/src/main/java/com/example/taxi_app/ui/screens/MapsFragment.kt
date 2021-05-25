package com.example.taxi_app.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.example.taxi_app.R
import com.example.taxi_app.`interface`.ApiInterface
import com.example.taxi_app.database.*
import com.example.taxi_app.databinding.FragmentMapsBinding
import com.example.taxi_app.utilites.APP_ACTIVITY
import com.example.taxi_app.utilites.hideKeyboard
import com.example.taxi_app.utilites.showToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Suppress("UNREACHABLE_CODE", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "CAST_NEVER_SUCCEEDS", "NAME_SHADOWING", "DEPRECATION")
class MapsFragment : Fragment(R.layout.fragment_maps), OnMapReadyCallback {

    private lateinit var binding: FragmentMapsBinding
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101
    private lateinit var slidingPaneLayout: SlidingPaneLayout
    private lateinit var gMap: GoogleMap
    private lateinit var sType: String
    private var lat1: Double = 0.0
    private var long1: Double = 0.0
    private var lat2: Double = 0.0
    private var long2: Double = 0.0
    private var lat3: Double = 0.0
    private var long3: Double = 0.0
    private var flag: Int = 0

    private val value: Int = 0

    private lateinit var progressDialog: ProgressDialog

    private var distanceValue: Int = 0
    private var timeValue: Int = 0

    private var distanceValueCenterPoint: Int = 0
    private var timeValueCenterPoint: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapsBinding.bind(view)
        Places.initialize(APP_ACTIVITY, getString(R.string.google_maps_key))
        changeEdtAddress()
        binding.addCenterPoint.setOnClickListener {
            binding.centerLocation.isVisible = true
            binding.view.isVisible = true
            binding.deleteCenterPoint.isVisible = true
            binding.addCenterPoint.isVisible = false
            binding.liner.isBaselineAligned
            val params = binding.liner.layoutParams
            params.height = 1350
        }
        binding.deleteCenterPoint.setOnClickListener {
            binding.centerLocation.isVisible = false
            binding.view.isVisible = false
            binding.deleteCenterPoint.isVisible = false
            binding.addCenterPoint.isVisible = true
            binding.centerLocation.text.clear()
            binding.endLocation.text.clear()
            binding.coastRide.text = getString(R.string.coast_ride_description_maps_fragment)
            binding.timeRide.text = getString(R.string.time_ride_description_maps_fragment)
            binding.distanceRide.text = getString(R.string.dist_ride_description_maps_fragment)
            val params = binding.liner.layoutParams
            params.height = 1150
        }
        progressDialog = ProgressDialog(APP_ACTIVITY)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        val root = layoutInflater.inflate(R.layout.fragment_maps, container, false)
        initView(root)
    }

    private fun initView(root: View?) {
        slidingPaneLayout = root?.findViewById(R.id.mapFragment) as SlidingPaneLayout
    }

    //Change edt text
    private fun changeEdtAddress() {
        binding.startLocation.isFocusable = false
        binding.startLocation.setOnClickListener {
            sType = "start"
            startGooglePlaces()
        }

        binding.endLocation.isFocusable = false
        binding.endLocation.setOnClickListener {
            sType = "end"
            startGooglePlaces()
        }

        binding.centerLocation.isFocusable = false
        binding.centerLocation.setOnClickListener {
            sType = "center"
            startGooglePlaces()
        }
    }

    //Create google places for search address
    private fun startGooglePlaces() {
        val fields = listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG)
        val intent =
            Intent(Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields).build(
                APP_ACTIVITY))
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val place = Autocomplete.getPlaceFromIntent(data!!)
            changeGooglePlaces(place)
        } else if (requestCode == AutocompleteActivity.RESULT_ERROR) {
            val status = Autocomplete.getStatusFromIntent(data!!)
            showToast(status.statusMessage)
        }
    }

    private fun changeGooglePlaces(place: Place) {
        when (sType) {
            "start" -> {
                flag++
                binding.startLocation.setText(place.address)
                gMap.addMarker(MarkerOptions().position(place.latLng))
                var destination = place.latLng.toString()
                destination = destination.replace("lat/lng:", "")
                destination = destination.replace("(", "")
                destination = destination.replace(")", "")
                val split = destination.split(",")
                lat1 = split[0].toDouble()
                long1 = split[1].toDouble()
            }
            "end" -> {
                flag++
                binding.endLocation.setText(place.address)
                gMap.addMarker(MarkerOptions().position(place.latLng))
                var destination = place.latLng.toString()
                destination = destination.replace("lat/lng:", "")
                destination = destination.replace("(", "")
                destination = destination.replace(")", "")
                val split = destination.split(",")
                lat2 = split[0].toDouble()
                long2 = split[1].toDouble()
            }
            "center" -> {
                flag++
                binding.centerLocation.setText(place.address)
                gMap.addMarker(MarkerOptions().position(place.latLng))
                var destination = place.latLng.toString()
                destination = destination.replace("lat/lng:", "")
                destination = destination.replace("(", "")
                destination = destination.replace(")", "")
                val split = destination.split(",")
                lat3 = split[0].toDouble()
                long3 = split[1].toDouble()
            }
        }
        if (flag >= 2) {
            val origin = "$lat1,$long1"
            val dest = "$lat2,$long2"
            val center = "$lat3,$long3"
            distance(origin, dest, center)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun distance(
        origin: String,
        dest: String,
        center: String,
    ) {
        val apiService = ApiInterface()

        if (binding.centerLocation.text.isEmpty()) {
            GlobalScope.launch(Dispatchers.Main) {
                val currentDistanceResponse =
                    apiService.getCurrentDistanceAsync(origin, dest).await()
                Log.e(TAG, currentDistanceResponse.toString())
                distanceValue = currentDistanceResponse.rows[value].elements[value].distance.value
                timeValue = currentDistanceResponse.rows[value].elements[value].duration.value
                val coastRide = 25 + (distanceValue / 1000) * 8
                val distantRide = "${distanceValue / 1000}, ${distanceValue % 1000}"
                val timeRide = timeValue / 60
                binding.coastRide.text = getString(R.string.coast_ride_description_maps_fragment) + " " + coastRide + " " + getString(R.string.grn)
                binding.distanceRide.text = getString(R.string.dist_ride_description_maps_fragment) + " " + distantRide + " " + getString(R.string.meters)
                binding.timeRide.text = getString(R.string.time_ride_description_maps_fragment) + " " + timeRide + " " + getString(R.string.minutes)
            }
        } else if (binding.centerLocation.text.isNotEmpty()) {
            GlobalScope.launch(Dispatchers.Main) {
                val currentDistanceResponseCenterPoint = apiService.getCurrentDistanceCenterPointAsync(center, dest).await()
                Log.e(TAG, currentDistanceResponseCenterPoint.toString())
                val coastRideCenterPoint = 25 + ((distanceValue + distanceValueCenterPoint) / 1000) * 8
                val distantRideCenterPoint = "${(distanceValue + distanceValueCenterPoint) / 1000}, ${(distanceValue + distanceValueCenterPoint) % 1000}"
                val timeRideCenterPoint = (timeValue + timeValueCenterPoint) / 60
                distanceValueCenterPoint = currentDistanceResponseCenterPoint.rows[value].elements[value].distance.value
                timeValueCenterPoint = currentDistanceResponseCenterPoint.rows[value].elements[value].duration.value
                binding.coastRide.text = getString(R.string.coast_ride_description_maps_fragment) + " " + coastRideCenterPoint + " " + getString(R.string.grn)
                binding.distanceRide.text = getString(R.string.dist_ride_description_maps_fragment) + " " + distantRideCenterPoint + " " + getString(R.string.meters)
                binding.timeRide.text = getString(R.string.time_ride_description_maps_fragment) + " " + timeRideCenterPoint + " " + getString(R.string.minutes)
            }
        }
    }

    //Order a taxi
    private fun createRide() {
        val dateMap = mutableMapOf<String, Any>()
        dateMap[CHILD_START] = binding.startLocation.text.toString()
        if (binding.centerLocation.text.toString().isNotEmpty()) {
            dateMap[CHILD_CENTER] = binding.centerLocation.text.toString()
        }
        dateMap[CHILD_END] = binding.endLocation.text.toString()
        dateMap[CHILD_NAME] = USER.name_user
        dateMap[CHILD_EMAIL] = USER.email_user
        dateMap[CHILD_PHONE] = USER.phone_user
        dateMap[CHILD_COAST_RIDE] = binding.coastRide.text
        dateMap[CHILD_PAY_METHOD] = USER.pay_method

        val keyRide = REF_DATABASE_ROOT.child(NODE_PRE_RIDES).push().key.toString()

        val dateMapKey = mutableMapOf<String, Any>()
        dateMapKey[CHILD_KEY_RIDE] = keyRide

        REF_DATABASE_ROOT.child(NODE_KEY_RIDE).child(keyRide).updateChildren(dateMapKey)

        REF_DATABASE_ROOT.child(NODE_PRE_RIDES).child(keyRide).updateChildren(dateMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    hideKeyboard()
                    progressDialog.show()

                    Handler().postDelayed({

                        progressDialog.hide()

                    }, 5000)

                } else showToast(getString(R.string.something_went_wrong_toast))
            }
    }

    override fun onResume() {
        super.onResume()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(APP_ACTIVITY)
        fetchLocation()
        binding.mapBtnCreateRide.setOnClickListener { createRide() }
        val uid = AUTH.currentUser?.uid.toString()
        REF_DATABASE_ROOT.child(NODE_USERS).child(uid)
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("SetTextI18n")
                override fun onDataChange(snapshot: DataSnapshot) {
                    val payMethod = snapshot.child(CHILD_PAY_METHOD).value.toString()
                    binding.payMethod.text =
                        "${getString(R.string.pay_method_description_text_view)}: $payMethod"
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    //Add tracking
    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        if (ActivityCompat.checkSelfPermission(
                APP_ACTIVITY,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                APP_ACTIVITY,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        googleMap.uiSettings.isRotateGesturesEnabled = false
        googleMap.isMyLocationEnabled = true
        googleMap.isTrafficEnabled = true
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
    }

    //Fetch user location
    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                APP_ACTIVITY, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                APP_ACTIVITY, Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(APP_ACTIVITY,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                val mapFragment =
                    childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                mapFragment?.getMapAsync(this)
            }
        }
    }

    //Permission
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation()
            }
        }
    }
}