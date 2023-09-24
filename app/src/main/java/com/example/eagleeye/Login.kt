package com.example.eagleeye

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // Creating an object for the database process.
        val db = DBHelper(this, null)


    }

}