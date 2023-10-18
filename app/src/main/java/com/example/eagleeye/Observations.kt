package com.example.eagleeye

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import android.database.sqlite.SQLiteDatabase
import android.database.Cursor
import com.example.eagleeye.DBHelper.Companion.Sighting_Table


class Observations : AppCompatActivity() {

    private lateinit var btnHomeScreen: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.observations) // Connecting to the observations.xml

        /* Returning to the home screen */
        /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
        btnHomeScreen = findViewById<ImageView>(R.id.homeBtn);

        btnHomeScreen.setOnClickListener {
            // Add your code here to handle the click event
            // For example, you can open a new activity or perform some action.

            // Taking the user to the homepage.
            val intent = Intent(this@Observations, Home::class.java)
            startActivity(intent);

        }
        /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
        // Retrieve data from the database and get a cursor
        val dbHelper = DBHelper(this, null)
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT Id as _id, BirdName, LatinName, Location, BirdPhotoBlob, Timestamp FROM $Sighting_Table", null)

        // Find the ListView and set up the custom adapter
        val listView: ListView = findViewById(R.id.listViewObservations)
        val adapter = SightingCursorAdapter(this, cursor, 0)
        listView.adapter = adapter


    }







}