package com.example.eagleeye

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        // Retrieve data from the database and get a cursor
        val dbHelper = DBHelper(this, null)
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT Id as _id, BirdName, LatinName, Location, BirdPhotoBlob, Timestamp FROM ${DBHelper.Sighting_Table}", null)

        // Find the ListView and set up the custom adapter
        val listView1: ListView = findViewById(R.id.listViewObservations1)
        val listView2: ListView = findViewById(R.id.listViewObservations2)
        val listView3: ListView = findViewById(R.id.listViewObservations3)
        val adapter = SightingCursorAdapter(this, cursor, 0)
        listView1.adapter = adapter
        listView2.adapter = adapter
        listView3.adapter = adapter
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

        // Retrieve data from the database and get a cursor
        val dbHelper = DBHelper(this, null)
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT Id as _id, BirdName, LatinName, Location, BirdPhotoBlob, Timestamp FROM ${DBHelper.Sighting_Table}", null)

        // Find the ListView and set up the custom adapter
        val listView1: ListView = findViewById(R.id.listViewObservations1)
        val listView2: ListView = findViewById(R.id.listViewObservations2)
        val listView3: ListView = findViewById(R.id.listViewObservations3)
        val adapter = SightingCursorAdapter(this, cursor, 0)
        listView1.adapter = adapter
        listView2.adapter = adapter
        listView3.adapter = adapter

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

        // Retrieve data from the database and get a cursor
        val dbHelper = DBHelper(this, null)
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT Id as _id, BirdName, LatinName, Location, BirdPhotoBlob, Timestamp FROM ${DBHelper.Sighting_Table}", null)

        // Find the ListView and set up the custom adapter
        val listView1: ListView = findViewById(R.id.listViewObservations1)
        val listView2: ListView = findViewById(R.id.listViewObservations2)
        val listView3: ListView = findViewById(R.id.listViewObservations3)
        val adapter = SightingCursorAdapter(this, cursor, 0)
        listView1.adapter = adapter
        listView2.adapter = adapter
        listView3.adapter = adapter

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

        // Retrieve data from the database and get a cursor
        val dbHelper = DBHelper(this, null)
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT Id as _id, BirdName, LatinName, Location, BirdPhotoBlob, Timestamp FROM ${DBHelper.Sighting_Table}", null)

        // Find the ListView and set up the custom adapter
        val listView1: ListView = findViewById(R.id.listViewObservations1)
        val listView2: ListView = findViewById(R.id.listViewObservations2)
        val listView3: ListView = findViewById(R.id.listViewObservations3)
        val adapter = SightingCursorAdapter(this, cursor, 0)
        listView1.adapter = adapter
        listView2.adapter = adapter
        listView3.adapter = adapter

        try {
            val intent = Intent(this@Home, Hotspot::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Toast.makeText(this, "This button works!", Toast.LENGTH_SHORT).show()

    }

}
