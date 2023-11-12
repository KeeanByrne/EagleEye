package com.example.eagleeye

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eagleeye.adapters.SightingFirebaseAdapter
import com.example.eagleeye.models.Sighting
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Observations : AppCompatActivity() {

    private lateinit var btnHomeScreen: ImageView
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: SightingFirebaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.observations)

        // Initialize Firebase components
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Find views
        btnHomeScreen = findViewById(R.id.homeBtn)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Setup click listener for home button
        btnHomeScreen.setOnClickListener {
            val intent = Intent(this@Observations, Home::class.java)
            startActivity(intent)
        }

        // Load observations based on the current user's UID
        loadObservations(auth.currentUser?.uid)
    }

    private fun loadObservations(userId: String?) {
        Log.d("Observations", "loadObservations started. userId: $userId")

        if (userId != null) {
            // Query Firestore to get observations for the current user
            val query: Query = firestore.collection("sighting")
                .whereEqualTo("UserID", userId)

            // Create FirestoreRecyclerOptions using the sightings list
            val options = FirestoreRecyclerOptions.Builder<Sighting>()
                .setQuery(query, Sighting::class.java)
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

    // Override onStart and onStop to start and stop observing changes when the activity is in the foreground
    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
