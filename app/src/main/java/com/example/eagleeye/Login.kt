package com.example.eagleeye

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eagleeye.databinding.HomepageBinding

class Login : AppCompatActivity() {

    private lateinit var btn_login: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // Button to check if the user account exists.
        btn_login = findViewById(R.id.btnLogin);

        btn_login.setOnClickListener {
            var text_username = findViewById<EditText>(R.id.editUsername);
            var username = text_username.text.toString();

            var text_password = findViewById<EditText>(R.id.editPassword);
            var password = text_password.text.toString();

            // Checking if the user account exists and then logging the user in.
            checkUserAccount(username, password);

        }



    }

    // Checking if the user account exists.
    fun checkUserAccount(usernameCheck: String, passwordCheck: String) {

        // Creating an object for the database process to read the information.
        val db = DBHelper(this, null)

        val userExists = db.checkUser(usernameCheck, passwordCheck);

        // Checking if the user exists.
        if(userExists) {
            Toast.makeText(this, "User exists!", Toast.LENGTH_SHORT).show()

            // Creating the session for the user by storing the ID.
            startSession(usernameCheck);

            // Taking the user to the homepage.
            val intent = Intent(this@Login, Home::class.java)
            startActivity(intent)

        } else {
            Toast.makeText(this, "User does not exist!", Toast.LENGTH_SHORT).show()
        }


    }

    // Starting the session for the user to log in.
    fun startSession(usernameCheck: String) {
        val sharedPreference: SharedPreferences = getSharedPreferences("MY_SESSION", Context.MODE_PRIVATE);

        val db = DBHelper(this,null)

        // Searching the ID of the username.
        val id = db.returnID(usernameCheck);

        val editor: SharedPreferences.Editor = sharedPreference.edit();

        editor.putString("ID", id);
    }

}