package com.example.eagleeye

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.concurrent.thread

class Home: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)
    }


    // Going to the observations page
    fun handleObservationPageClick(view: View) {
        // Add your code here to handle the click event in the backend
        // For example, you can open a new activity or perform some action.

        // Taking the user to the homepage
        try {
            val intent = Intent(this@Home, Observations::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Toast.makeText(this, "This button works!", Toast.LENGTH_SHORT).show()
    }

    fun handleSightingPageClick(view: View) {


        // Taking the user to the Record Sightings Page
        try {
            val intent = Intent(this@Home, Sighting::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    // Going to the settings page
    fun handleSettingsPageClick(view: View) {
        // Taking the user to the homepage

        try {
            val intent = Intent(this@Home, Settings::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Toast.makeText(this, "This button works!", Toast.LENGTH_SHORT).show()

    }

    // Going to the Hotspot page.
    fun handleHotspotPageClick(view: View) {
        // Taking the user to hotspot page.

        try {
            val intent = Intent(this@Home, Hotspot::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Toast.makeText(this, "This button works!", Toast.LENGTH_SHORT).show()

    }

}
