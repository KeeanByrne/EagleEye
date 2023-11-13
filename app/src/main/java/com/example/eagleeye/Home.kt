package com.example.eagleeye

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
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
        val db = Firebase.firestore
    }


    // Going to the observations page
    fun handleObservationPageClick(view: View) {
        // Taking the user to the homepage
        try {
            val intent = Intent(this@Home, Observations::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun handleSightingPageClick(view: View) {

        try {
            val intent = Intent(this@Home, Sighting::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    // Going to the settings page
    fun handleSettingsPageClick(view: View) {

        try {
            val intent = Intent(this@Home, Settings::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

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

    }

}
