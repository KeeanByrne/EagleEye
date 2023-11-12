package com.example.eagleeye.adapters
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eagleeye.R
import com.example.eagleeye.models.Sighting
import com.firebase.ui.firestore.FirestoreArray
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.squareup.picasso.Picasso

class SightingFirebaseAdapter(
    options: FirestoreRecyclerOptions<Sighting>,
    context: Context
) : FirestoreRecyclerAdapter<Sighting, SightingFirebaseAdapter.SightingViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SightingViewHolder {
        Log.d("Sighting Check", "SightingViewHolderReturned")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return SightingViewHolder(view)
    }

    override fun onBindViewHolder(holder: SightingViewHolder, position: Int, model: Sighting) {
        Log.d("Sighting Check", "Super Bind called")
        holder.bind(model)
    }

    inner class SightingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //private val imageBirdPhoto: ImageView = itemView.findViewById(R.id.imageBirdPhoto)
        private val textBirdName: TextView = itemView.findViewById(R.id.birdNameTextView)
        private val textLatinName: TextView = itemView.findViewById(R.id.latinNameTextView)
        private val textLocation: TextView = itemView.findViewById(R.id.locationTextView)

        fun bind(sighting: Sighting) {
            // Use Picasso or Glide to load the image into the ImageView
            // Example: Picasso.get().load(sighting.imageURL).into(imageBirdPhoto)
            Log.d("Sighting Check", "Bind called")
            textBirdName.text = "Bird Name: ${sighting.BirdName}"
            textLatinName.text = "Latin Name: ${sighting.LatinName}"
            textLocation.text = "Location: ${sighting.Location}"
        }
    }
}
/*
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eagleeye.R
import com.example.eagleeye.models.Sighting
import com.firebase.ui.firestore.FirestoreArray
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.squareup.picasso.Picasso

class SightingFirebaseAdapter(
    options: FirestoreRecyclerOptions<Sighting>,
    context: Context
) : FirestoreRecyclerAdapter<Sighting, SightingFirebaseAdapter.SightingViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SightingViewHolder {
        Log.d("Sighting Check", "SightingViewHolderReturned")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return SightingViewHolder(view)
    }

    override fun onBindViewHolder(holder: SightingViewHolder, position: Int, model: Sighting) {
        Log.d("Sighting Check", "Super Bind called")
        holder.bind(model)
    }

    inner class SightingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageBirdPhoto: ImageView = itemView.findViewById(R.id.imageBirdPhoto)
        private val textBirdName: TextView = itemView.findViewById(R.id.textBirdName)
        private val textLatinName: TextView = itemView.findViewById(R.id.textLatinName)
        private val textLocation: TextView = itemView.findViewById(R.id.textLocation)

        fun bind(sighting: Sighting) {
            // Use Picasso or Glide to load the image into the ImageView
            // Example: Picasso.get().load(sighting.imageURL).into(imageBirdPhoto)
            Log.d("Sighting Check", "Bind called")
            textBirdName.text = "Bird Name: ${sighting.BirdName}"
            textLatinName.text = "Latin Name: ${sighting.LatinName}"
            textLocation.text = "Location: ${sighting.Location}"
        }
    }
}*/
