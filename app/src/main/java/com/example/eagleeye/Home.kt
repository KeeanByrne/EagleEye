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
import com.example.eagleeye.adapters.SightingFirebaseAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.concurrent.thread

class Home: AppCompatActivity() {

    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: SightingFirebaseAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        //Setting up Firestore and Authentication
        val db = Firebase.firestore
        val auth = Firebase.auth
        val userid = auth.currentUser?.uid
        Log.d("CurrentUser", "ID: ${userid}")

        // Initialize Firebase components
        firestore = FirebaseFirestore.getInstance()
        recyclerView = findViewById(R.id.recyclerView1)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Load observations based on the current user's UID
        loadObservations(auth.currentUser?.uid)




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

    private fun loadObservations(userId: String?) {
        Log.d("Observations", "loadObservations started. userId: $userId")

        if (userId != null) {
            // Query Firestore to get observations for the current user
            val query: Query = firestore.collection("sighting")
                .whereEqualTo("UserID", userId)

            // Create FirestoreRecyclerOptions using the sightings list
            val options = FirestoreRecyclerOptions.Builder<com.example.eagleeye.models.Sighting>()
                .setQuery(query, com.example.eagleeye.models.Sighting::class.java)
                .build()

            // Create a custom adapter to populate the RecyclerView
            adapter = SightingFirebaseAdapter(options, this)

            // Attach the adapter to the RecyclerView
            recyclerView.adapter = adapter

            Log.d("Observations", "Adapter set for RecyclerView.")
        } else {
            Log.e("Observations", "userId is null.")
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}
