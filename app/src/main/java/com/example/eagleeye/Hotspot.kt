package com.example.eagleeye

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Hotspot : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var btnHomeScreen: ImageView
    private var myMap: GoogleMap? = null // Change to var to allow assignment
    private val REQUEST_CODE = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotspots)

        /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

        // Going back to the homescreen.
        btnHomeScreen = findViewById(R.id.homeBtn)

        btnHomeScreen.setOnClickListener {
            val intent = Intent(this@Hotspot, Home::class.java)
            startActivity(intent)
        }

        /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap // Assign the googleMap object to myMap

        // Add a marker in Sydney and move the camera

        // Initialize the location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Get the last known location
        fusedLocationClient.lastLocation
            .addOnSuccessListener(this@Hotspot) { location ->
                if (location != null) {
                    val my_mark = LatLng(location.latitude, location.longitude)

                    // Add a marker for the user's location
                    myMap?.addMarker(MarkerOptions().position(my_mark).title("My Location"))

                    val zoomLevel = 12.0f // Adjust the zoom level as needed
                    myMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(my_mark, zoomLevel))

                    // Launch a coroutine to populate hotspots
                    CoroutineScope(Dispatchers.IO).launch {
                        val hotspotList = populateHotspots(location.latitude, location.longitude)
                        displayHotspots(hotspotList)
                    }
                }
            }
    }


    // -33.9248683, 18.424055
    private suspend fun populateHotspots(latitude: Double, longitude: Double): List<eBirdAPIHelper.HotspotData> {
        return try {
            val eBirdAPIHelper = eBirdAPIHelper()
            val hotSpotList = eBirdAPIHelper.getNearbyHotspot(latitude.toString(), longitude.toString())
            hotSpotList
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle exceptions as needed
            // Return an appropriate result or throw an exception if necessary
            emptyList() // Return an empty list as a default value
        }
    }

    private suspend fun displayHotspots(hotspotList: List<eBirdAPIHelper.HotspotData>) {
        val markerOptionsList = hotspotList.map { hotspotData ->
            MarkerOptions()
                .position(LatLng(hotspotData.latitude, hotspotData.longitude))
                .title(hotspotData.name)
        }

        withContext(Dispatchers.Main) {
            markerOptionsList.forEach { markerOptions ->
                myMap?.addMarker(markerOptions)
            }
        }
    }
}

private fun SupportMapFragment.getMapAsync(hotspot: Hotspot) {

}
