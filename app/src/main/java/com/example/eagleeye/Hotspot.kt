package com.example.eagleeye

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URLEncoder



class Hotspot : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var btnHomeScreen: ImageView
    private lateinit var btnGetDirections: Button
    private lateinit var btnViewHotspot: Button
    private lateinit var btnMyLocation: Button


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
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
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
        // Getting the Directions from the origin to destination.
        btnGetDirections = findViewById(R.id.btnGetDirections)

        btnGetDirections.setOnClickListener {

            myMap?.clear()

            val destination_marker = LatLng(latitudeDestination, longitudeDestination)
            val origin_marker = LatLng(latitudeOrigin, longitudeOrigin)

            // Add a marker for the user's location
            myMap?.addMarker(MarkerOptions().position(destination_marker).title("Destination"))
            myMap?.addMarker(MarkerOptions().position(origin_marker).title("Origin"))

            // Showing the route
            val URL = getDirectionURL(origin_marker, destination_marker)
            GetDirection(URL).execute()

        }

        /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
        // Viewing all the hotspots
        btnViewHotspot = findViewById(R.id.btnViewHotspots)

        btnViewHotspot.setOnClickListener {
            myMap?.clear()

            getUserLocationAndAddMarkers()

        }
        /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
        btnMyLocation = findViewById(R.id.btnCurrentLocation)

        btnMyLocation.setOnClickListener {
            myMap?.clear()

            getMyLocationOnly()
        }

    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap // Assign the googleMap object to myMap
        myMap?.setOnMarkerClickListener(this) // Set the marker click listener
        // Add a marker in Sydney and move the camera

        getUserLocationAndAddMarkers()

    }

    // Returning the current location only
    private fun getMyLocationOnly() {
        // Initialize the location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Get the last known location
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener(this@Hotspot) { location ->
                if (location != null) {
                    val my_mark = LatLng(location.latitude, location.longitude)

                    latitudeOrigin = location.latitude
                    longitudeOrigin = location.longitude

                    val markerColor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN) // Change to the desired color (e.g., BitmapDescriptorFactory.HUE_GREEN)
                    val markerOptions = MarkerOptions()
                        .position(my_mark) // Replace with your marker's position
                        .title("My Location")
                        .icon(markerColor)
                    myMap?.addMarker(markerOptions)

                    val zoomLevel = 16.0f // Adjust the zoom level as needed
                    myMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(my_mark, zoomLevel))

                }
            }
    }

    private fun getUserLocationAndAddMarkers() {
        // Initialize the location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Get the last known location
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
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



    fun getDirectionURL(origin: LatLng, dest: LatLng): String {

        val encodedOrigin = URLEncoder.encode("${origin.latitude},${origin.longitude}", "UTF-8")
        val encodedDestination = URLEncoder.encode("${dest.latitude},${dest.longitude}", "UTF-8")
        val encodedAPI = URLEncoder.encode("AIzaSyD16EaDbQvz8oFV7pZfLDGUntO51P5-LpE")
        val url = "https://maps.googleapis.com/maps/api/directions/json" +
                "?origin=$encodedOrigin" +
                "&destination=$encodedDestination" +
                "&sensor=false" +
                "&mode=driving" +
                "&key=$encodedAPI"

        return url
    }

    /*private inner class GetDirection(val url : String) : AsyncTask<Void, Void, List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()
            Log.d("GoogleMap" , " data : $data")

            println("Testing Directions Route")

            val result =  ArrayList<List<LatLng>>()
            try{
                val respObj = Gson().fromJson(data,GoogleMapDTO::class.java)

                val path =  ArrayList<LatLng>()

                for (i in 0..(respObj.routes[0].legs[0].steps.size-1)){
//                    val startLatLng = LatLng(respObj.routes[0].legs[0].steps[i].start_location.lat.toDouble()
//                            ,respObj.routes[0].legs[0].steps[i].start_location.lng.toDouble())
//                    path.add(startLatLng)
//                    val endLatLng = LatLng(respObj.routes[0].legs[0].steps[i].end_location.lat.toDouble()
//                            ,respObj.routes[0].legs[0].steps[i].end_location.lng.toDouble())
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices){
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.BLUE)
                lineoption.geodesic(true)
            }

            runOnUiThread {
                myMap?.addPolyline(lineoption)
            }
        }
    }*/

    private inner class GetDirection(val url: String) : AsyncTask<Void, Void, List<List<LatLng>>>() {
        private lateinit var respObj: GoogleMapDTO

        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()
            Log.d("GoogleMap", "API Response: $data")

            val result = ArrayList<List<LatLng>>()
            try {
                respObj = Gson().fromJson(data, GoogleMapDTO::class.java)

                if (respObj.routes.isNotEmpty()) {
                    for ((index, route) in respObj.routes.withIndex()) {
                        Log.d("GoogleMap", "Route $index:")
                        Log.d("GoogleMap", " - Legs: ${route.legs.size}")
                        Log.d("GoogleMap", " - Distance: ${route.legs[0].distance.text}")
                        Log.d("GoogleMap", " - Duration: ${route.legs[0].duration.text}")

                        val path = ArrayList<LatLng>()
                        for (i in route.legs[0].steps.indices) {
                            path.addAll(decodePolyline(route.legs[0].steps[i].polyline.points))
                        }
                        result.add(path)
                    }
                } else {
                    Log.d("GoogleMap", "No routes found in the response.")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            for (i in result.indices) {
                val lineoption = PolylineOptions()
                lineoption.addAll(result[i])
                lineoption.width(20f)
                lineoption.color(Color.BLUE)
                lineoption.geodesic(true)
                myMap?.addPolyline(lineoption)
            }

            // Assuming there is at least one route
            if (::respObj.isInitialized && respObj.routes.isNotEmpty() && respObj.routes[0].legs.isNotEmpty()) {
                val distance = respObj.routes[0].legs[0].distance.text
                val duration = respObj.routes[0].legs[0].duration.text

                // Show distance and duration in a popup
                val alertDialogBuilder = AlertDialog.Builder(this@Hotspot)
                alertDialogBuilder.setTitle("Route Information")
                alertDialogBuilder.setMessage("Distance: $distance\nDuration: $duration")

                alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
                    // Do something when OK button is pressed
                }

                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            } else {
                Log.d("GoogleMap", "No valid route information.")
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


 public fun decodePolyline(encoded: String): List<LatLng> {

        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }

        return poly
    }

}

private fun SupportMapFragment.getMapAsync(hotspot: Hotspot) {

}
