package com.example.eagleeye

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity


class Settings : AppCompatActivity() {

    /*private lateinit var btnHomeScreen: ImageView*/

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings) // Connecting to the settings.xml
        populateMetricSpinner()      //Populating the Metric spinner
        populateDistanceSpinner()   //Populating the Max Distance spinner


        /* Returning to the home screen */
        /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
        /*btnHomeScreen = findViewById(R.id.homeBtn);

        btnHomeScreen.setOnClickListener {
            // Add your code here to handle the click event
            // For example, you can open a new activity or perform some action.

            // Taking the user to the homepage.
            val intent = Intent(this@Settings, Home::class.java)
            startActivity(intent);

        };*/
        /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
        val btnSaveAccount = findViewById<Button>(R.id.btnSaveAccount);
        btnSaveAccount.setOnClickListener {

        }
    }

    // Populating the unit spinner with Km and Mi.
    fun populateMetricSpinner() {

        // List of distance units
        val options = arrayOf("Km", "Mi")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = findViewById<Spinner>(R.id.spinnerMetric)
        spinner.adapter = adapter

    }

    fun populateDistanceSpinner() {

        // List of distance units
        val options = arrayOf("50", "100", "150", "200")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = findViewById<Spinner>(R.id.spinnerDistance)
        spinner.adapter = adapter

    }

}