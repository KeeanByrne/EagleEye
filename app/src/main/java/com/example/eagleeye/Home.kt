package com.example.eagleeye

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Home: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

    }

    fun handleObservationPageClick(view: View) {
        // Add your code here to handle the click event in the backend
        // For example, you can open a new activity or perform some action.

        // Taking the user to the homepage.


        try {
            val intent = Intent(this@Home, Observations::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Toast.makeText(this, "This button works!", Toast.LENGTH_SHORT).show()

    }

}