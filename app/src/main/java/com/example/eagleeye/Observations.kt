package com.example.eagleeye

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class Observations : AppCompatActivity() {

    private lateinit var btnHomeScreen: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.observations) // Connecting to the observations.xml

        btnHomeScreen = findViewById<ImageView>(R.id.homeBtn);

        btnHomeScreen.setOnClickListener {
            // Add your code here to handle the click event
            // For example, you can open a new activity or perform some action.

            // Taking the user to the homepage.
            val intent = Intent(this@Observations, Home::class.java)
            startActivity(intent);

        };


    }





}