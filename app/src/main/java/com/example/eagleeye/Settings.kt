package com.example.eagleeye

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class Settings : AppCompatActivity() {

    /*private lateinit var btnHomeScreen: ImageView*/

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings) // Connecting to the settings.xml
        populateMetricSpinner()      //Populating the Metric spinner
        populateDistanceSpinner()   //Populating the Max Distance spinner

        //Logout method
        val logoutUtil = LogoutUtil(this)

        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnLogout.setOnClickListener {
            logoutUtil.showLogoutConfirmationDialog {
                logoutUtil.logout() // Call the logout function when the user confirms
            }
        }



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
        /*val btnSaveAccount = findViewById<Button>(R.id.btnSaveAccount);
        val db = DBHelper(this, null);
        btnSaveAccount.setOnClickListener {
            val NewSettings = SettingsData();
            NewSettings.ProfilePic = "New Pic";
            NewSettings.Metric = findViewById<Spinner>(R.id.spinnerMetric).selectedItem.toString();
            NewSettings.MaxDistance = findViewById<Spinner>(R.id.spinnerDistance).selectedItem.toString().toInt();
            NewSettings.Username = findViewById<EditText>(R.id.editUsername).text.toString();
            NewSettings.UserID = db.returnID(findViewById<EditText>(R.id.editUsername).text.toString());
            NewSettings.Password = findViewById<EditText>(R.id.editPassword).text.toString();
            db.updateSettings(db.returnID(findViewById<EditText>(R.id.editUsername).text.toString()), NewSettings)
        }*/
    }

    fun logout() {

        // Navigate the user to the login screen
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
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