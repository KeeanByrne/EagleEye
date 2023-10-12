package com.example.eagleeye

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity


class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings) // Connecting to the settings.xml
        populateUnitSpinner(); // Populating the spinner

    }

    // Populating the unit spinner with Km and Mi.
    fun populateUnitSpinner() {

        // List of distance units
        val options = arrayOf("Km", "Mi")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = findViewById<Spinner>(R.id.spinnerMetric)
        spinner.adapter = adapter

    }
}