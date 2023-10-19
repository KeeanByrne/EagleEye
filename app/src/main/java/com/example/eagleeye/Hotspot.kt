package com.example.eagleeye

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Hotspot : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var btnHomeScreen: ImageView
    private lateinit var btnGetDirections: Button
    private var myMap: GoogleMap? = null // Change to var to allow assignment
    private val REQUEST_CODE = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitudeDestination: Double = 0.0
    private var longitudeDestination: Double = 0.0
    private var latitudeOrigin: Double = 0.0
    private var longitudeOrigin: Double = 0.0

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
        btnGetDirections = findViewById(R.id.btnGetDirections)

        btnGetDirections.setOnClickListener {

            myMap?.clear()

            val destination_marker = LatLng(latitudeDestination, longitudeDestination)
            val origin_marker = LatLng(latitudeOrigin, longitudeOrigin)

            // Add a marker for the user's location
            myMap?.addMarker(MarkerOptions().position(destination_marker).title("Destination"))
            myMap?.addMarker(MarkerOptions().position(origin_marker).title("Origin"))


        }

    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap // Assign the googleMap object to myMap
        myMap?.setOnMarkerClickListener(this) // Set the marker click listener
        // Add a marker in Sydney and move the camera

        // Initialize the location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Get the last known location
        fusedLocationClient.lastLocation
            .addOnSuccessListener(this@Hotspot) { location ->
                if (location != null) {
                    val my_mark = LatLng(location.latitude, location.longitude)

                    latitudeOrigin = location.latitude
                    longitudeOrigin = location.longitude

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

    override fun onMarkerClick(p0: Marker): Boolean {
        // Check if the clicked marker is not null
        if (p0 != null) {
            // Get the latitude and longitude of the clicked marker
            latitudeDestination = p0.position.latitude
            longitudeDestination = p0.position.longitude

            // Now you can save or use these coordinates as needed
            // For example, display in a Toast
            Toast.makeText(
                this@Hotspot,
                "Clicked Marker Location: Latitude = $latitudeDestination, Longitude = $longitudeDestination",
                Toast.LENGTH_SHORT
            ).show()

            // Return true to indicate that the marker click event has been handled
            return true
        }

        // Return false to indicate that the marker click event hasn't been handled
        return false
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
