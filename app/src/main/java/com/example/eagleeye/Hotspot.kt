package com.example.eagleeye

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Hotspot : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var btnHomeScreen: ImageView
    private var myMap: GoogleMap? = null // Change to var to allow assignment

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotspots)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        // Going back to the homescreen.
        btnHomeScreen = findViewById(R.id.homeBtn)

        btnHomeScreen.setOnClickListener {
            val intent = Intent(this@Hotspot, Home::class.java)
            startActivity(intent)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap // Assign the googleMap object to myMap

        // Add a marker in Sydney and move the camera
        val my_mark = LatLng(-33.8084826, 18.4763215)

        myMap?.addMarker(
            MarkerOptions()
                .position(my_mark)
                .title("My Marker")
        )

        val zoomLevel = 12.0f // Adjust the zoom level as needed
        myMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(my_mark, zoomLevel))


        // Launch a coroutine for loading the map
        CoroutineScope(Dispatchers.IO).launch {
            // Map loading logic
        }

        // Launch a separate coroutine for populating hotspots
        CoroutineScope(Dispatchers.IO).launch {
            val result = populateHotspots()
            val markerOptionsList = mutableListOf<MarkerOptions>()
            withContext(Dispatchers.Main) {

                result.forEach {  hotspotData ->

                    val markerLoop = MarkerOptions().position(LatLng(hotspotData.latitude, hotspotData.longitude)).title("${hotspotData.name}")
                    markerOptionsList.add(markerLoop)

                    /*
                    println("--------------------------------")
                    println("${hotspotData.name}")
                    println("${hotspotData.latitude}")
                    println("${hotspotData.longitude}")
                    println("--------------------------------")
                    */
                }

                for (markerOptions in markerOptionsList) {
                    myMap?.addMarker(markerOptions)
                }

            }
        }

    }

    suspend fun populateHotspots(): List<eBirdAPIHelper.HotspotData>{
        return withContext(Dispatchers.IO) {
            try {
                val eBirdAPIHelper = eBirdAPIHelper()
                val hotSpotList = eBirdAPIHelper.getNearbyHotspot("-33.8084826", "18.4763215")
                hotSpotList
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle exceptions as needed
                // Return an appropriate result or throw an exception if necessary
                emptyList() // Return an empty list as a default value
            }
        }
    }
}

private fun SupportMapFragment.getMapAsync(hotspot: Hotspot) {

}
