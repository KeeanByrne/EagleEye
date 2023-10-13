package com.example.eagleeye

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class Sighting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sighting)

        val homeBtn = findViewById<ImageView>(R.id.homeBtn1)
        homeBtn.setOnClickListener{
            val intent = Intent(this,Home::class.java)
            startActivity(intent)
        }

    }


}