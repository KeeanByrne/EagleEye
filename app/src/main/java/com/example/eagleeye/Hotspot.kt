package com.example.eagleeye

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class Hotspot : AppCompatActivity() {

    private lateinit var btnHomeScreen: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotspots)

        GlobalScope.launch(Dispatchers.IO) {

            val eBirdHelper = eBirdAPIHelper()

            eBirdHelper.testAPIConnection(); // test connection.

            // Requesting the nearby hotspots.
            var hotspotResults = eBirdHelper.getNearbyHotspot("-33.8084826", "18.4763215")

            hotspotResults.forEach { hotspotData ->

                println("ID: ${hotspotData.id}")
                println("Country: ${hotspotData.country}")
                println("Region: ${hotspotData.region}")
                println("Latitude: ${hotspotData.latitude}")
                println("Longitude: ${hotspotData.longitude}")
                println("Name: ${hotspotData.name}")

                println()
            }

        }



        /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
        // Going back to the homescreen.
        btnHomeScreen = findViewById<ImageView>(R.id.homeBtn);

        btnHomeScreen.setOnClickListener {
            // Add your code here to handle the click event
            // For example, you can open a new activity or perform some action.

            // Taking the user to the homepage.
            val intent = Intent(this@Hotspot, Home::class.java)
            startActivity(intent);

        };
        /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/

    }

}