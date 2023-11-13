package com.example.eagleeye

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eagleeye.adapters.SettingsFirebaseAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.concurrent.thread

class Home: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        //Setting up Firestore and Authentication
        val db = Firebase.firestore
        val auth = Firebase.auth
        val userid = auth.currentUser?.uid
        Log.d("CurrentUser", "ID: ${userid}")

        //Setting up the adapter to handle data from firebase
        val settingsFirebaseAdapter = SettingsFirebaseAdapter()

        //Setting up the homepage to display the user's correct information
        if (userid != null) {
            val profilePicView = findViewById<ImageView>(R.id.profilePic)
            settingsFirebaseAdapter.getSettings(userid) { settings ->
                if(settings.profilePic!=null)
                {
                    val imageByteArray = android.util.Base64.decode(settings.profilePic, android.util.Base64.DEFAULT)
                    // Load the byte array into the ImageView using Glide
                    Glide.with(profilePicView)
                        .load(imageByteArray)
                        .into(profilePicView)
                    } else {
                        // If no image is available, set a placeholder image
                        Glide.with(profilePicView)
                            .load(R.drawable.default_profile)
                            .into(profilePicView)
                    }
                }
            }

    }


    // Going to the observations page
    fun handleObservationPageClick(view: View) {
        // Taking the user to the homepage
        try {
            val intent = Intent(this@Home, Observations::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun handleSightingPageClick(view: View) {

        try {
            val intent = Intent(this@Home, Sighting::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    // Going to the settings page
    fun handleSettingsPageClick(view: View) {

        try {
            val intent = Intent(this@Home, Settings::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    // Going to the Hotspot page.
    fun handleHotspotPageClick(view: View) {
        // Taking the user to hotspot page.
        try {
            val intent = Intent(this@Home, Hotspot::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
