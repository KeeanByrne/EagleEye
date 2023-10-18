package com.example.eagleeye

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

    class SightingCursorAdapter(context: Context, cursor: Cursor?, flags: Int) : CursorAdapter(context, cursor, flags) {

        override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
            // Inflating your custom row layout for each list item
            return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        override fun bindView(view: View, context: Context, cursor: Cursor) {
            // Retrieve data from the cursor and populate the views in the custom row layout
            val birdNameTextView = view.findViewById<TextView>(R.id.textBirdName)
            val latinNameTextView = view.findViewById<TextView>(R.id.textLatinName)
            val locationTextView = view.findViewById<TextView>(R.id.textLocation)

            val birdNameIndex = cursor.getColumnIndex("BirdName")
            val latinNameIndex = cursor.getColumnIndex("LatinName")
            val locationIndex = cursor.getColumnIndex("Location")

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

