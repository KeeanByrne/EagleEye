package com.example.eagleeye.adapters
import android.content.Context
import android.graphics.BitmapFactory
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        private val birdPhotoImageView: ImageView = itemView.findViewById(R.id.birdPhotoImageView)

        fun bind(sighting: Sighting) {
            // Use Glide to load the image into the ImageView
            if (sighting.Image != null && sighting.Image.isNotEmpty()) {
                // Convert Base64 string to byte array
                val imageByteArray = android.util.Base64.decode(sighting.Image, android.util.Base64.DEFAULT)

                // Load the byte array into the ImageView using Glide
                Glide.with(itemView)
                    .load(imageByteArray)
                    .into(birdPhotoImageView)
            } else {
                // If no image is available, set a placeholder image
                Glide.with(itemView)
                    .load(R.drawable.placeholder_image)
                    .into(birdPhotoImageView)
            }
            textBirdName.text = "Bird Name: ${sighting.BirdName}"
            textLatinName.text = "Latin Name: ${sighting.LatinName}"
            textLocation.text = "Location: ${sighting.Location}"
        }


    }
}