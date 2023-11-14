package com.example.eagleeye

import android.content.Context
import android.database.Cursor
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.ImageView
import android.widget.TextView

    class SightingCursorAdapter(context: Context, cursor: Cursor?, flags: Int) : CursorAdapter(context, cursor, flags) {


        override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
            // Inflating your custom row layout for each list item
            val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

            // Log the ID of the ImageView
            val birdPhotoImageView = view.findViewById<ImageView>(R.id.birdPhotoImageView)
            Log.d("SightingCursorAdapter", "ImageView ID: ${birdPhotoImageView.id}")

            return view
        }


        override fun bindView(view: View, context: Context, cursor: Cursor) {
            // Retrieve data from the cursor and populate the views in the custom row layout
            val birdNameTextView = view.findViewById<TextView>(R.id.birdNameTextView)
            val latinNameTextView = view.findViewById<TextView>(R.id.latinNameTextView)
            val locationTextView = view.findViewById<TextView>(R.id.locationTextView)
            val birdPhotoImageView = view.findViewById<ImageView>(R.id.birdPhotoImageView)



            val birdNameIndex = cursor.getColumnIndex("BirdName")
            val latinNameIndex = cursor.getColumnIndex("LatinName")
            val locationIndex = cursor.getColumnIndex("Location")
            val birdPhotoIndex = cursor.getColumnIndex("BirdPhotoBlob")
            val birdPhotoData: ByteArray?

            if (birdPhotoIndex != -1) {
                birdPhotoData = cursor.getBlob(birdPhotoIndex)
            } else {
                birdPhotoData = null //Used to handle the case where "BirdPhotoBlob" doesn't exist
            }

            if (birdPhotoData != null) {
                val birdPhotoBitmap = BitmapFactory.decodeByteArray(birdPhotoData, 0, birdPhotoData.size)
                /*birdPhotoImageView.setImageBitmap(birdPhotoBitmap)*/
            } else {
                // Set a default image if no photo is available
                birdPhotoImageView.setImageResource(R.drawable.eagle_eye_df)
            }

            if (birdNameIndex != -1) {
                val birdName = cursor.getString(birdNameIndex)
                birdNameTextView.text = birdName
            } else {
                birdNameTextView.text = "N/A" // or handle the case where "BirdName" doesn't exist
            }

            if (latinNameIndex != -1) {
                val latinName = cursor.getString(latinNameIndex)
                latinNameTextView.text = latinName
            } else {
                latinNameTextView.text = "N/A" // or handle the case where "LatinName" doesn't exist
            }

            if (locationIndex != -1) {
                val location = cursor.getString(locationIndex)
                locationTextView.text = location
            } else {
                locationTextView.text = "N/A" // or handle the case where "Location" doesn't exist
            }

        }
    }

