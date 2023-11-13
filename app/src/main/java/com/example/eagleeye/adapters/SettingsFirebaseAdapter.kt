package com.example.eagleeye.adapters

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.example.eagleeye.models.Settings

class SettingsFirebaseAdapter {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val settingsCollection: CollectionReference = firestore.collection("settings")

    fun saveSettings(settings: Settings) {
        settingsCollection.document(settings.userID).set(settings)
            .addOnSuccessListener {
                // Handle success if needed
            }
            .addOnFailureListener { e ->
                // Handle failure if needed
            }
    }

    fun getSettings(userID: String, onComplete: (Settings) -> Unit) {
        Log.d("GetSettings", "Called")
        settingsCollection.whereEqualTo("userID", userID).get()
            .addOnSuccessListener { querySnapshot ->
                Log.d("GetSettings", "Pre If Check")
                Log.d("GetSettings", "${querySnapshot.documents}")
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val settings = document.toObject(Settings::class.java)
                    settings?.let { onComplete(it) }
                    Log.d("Settings", "Document Exists")
                } else {
                    Log.d("Settings", "Document No Exists")
                }
            }
            .addOnFailureListener { e ->
                Log.e("Settings", "Error fetching settings: ${e.message}", e)
                // Handle failure if needed
            }
    }


    // Add other methods as needed, like updating specific fields, etc.
}
