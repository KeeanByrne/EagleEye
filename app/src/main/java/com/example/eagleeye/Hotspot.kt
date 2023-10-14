package com.example.eagleeye

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Hotspot : AppCompatActivity() {

    private lateinit var btnHomeScreen: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotspots)


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