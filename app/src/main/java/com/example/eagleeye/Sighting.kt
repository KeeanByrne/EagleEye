package com.example.eagleeye

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import java.io.ByteArrayOutputStream


class Sighting : AppCompatActivity() {


    //Request code for camera permission
    private val CAMERA_PERMISSION_REQUEST = 1001

    private var capturedImageByteArray: ByteArray? = null


    private lateinit var birdNameEditText: EditText
    private lateinit var latinNameEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var photoButton: Button

    private val cameraLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val photo = result.data?.extras?.get("data") as Bitmap?

        if (photo != null) {

            // Convert the Bitmap to a byte array
            val stream = ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream)
            capturedImageByteArray = stream.toByteArray()
            stream.close()

            // Set the captured image to the ImageView with ID savedBirdPhoto
            val savedBirdPhotoImageView = findViewById<ImageView>(R.id.savedBirdPhoto)
            savedBirdPhotoImageView.setImageBitmap(photo)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sighting)

        val db = DBHelper(this, null)

        birdNameEditText = findViewById(R.id.birdNameTxt)
        latinNameEditText = findViewById(R.id.latinNameTxt)
        locationEditText = findViewById(R.id.sightingLocationTxt)
        saveButton = findViewById(R.id.btnSaveSighting)
        photoButton = findViewById(R.id.btnPhoto)

        photoButton.setOnClickListener(View.OnClickListener{

            if (hasCameraPermission()) {
                // Camera permission is granted, you can take a photo here
                takePhoto()
            } else {
                // Request camera permission
                requestCameraPermission()
            }
        })

        saveButton.setOnClickListener(View.OnClickListener {
            val birdName = birdNameEditText.text.toString()
            val latinName = latinNameEditText.text.toString()
            val location = locationEditText.text.toString()

            if (birdName.isNotEmpty() && latinName.isNotEmpty() && location.isNotEmpty()) {
                if (capturedImageByteArray != null) {
                    // Image is captured, so you can save it along with other information
                    db.addSightingWithImage(birdName, latinName, location, capturedImageByteArray)
                } else {
                    // Image is not captured, save other information without an image
                    db.addSighting(birdName, latinName, location)
                }

                // Optionally, you can clear the input fields after saving
                birdNameEditText.text.clear()
                latinNameEditText.text.clear()
                locationEditText.text.clear()
                capturedImageByteArray = null

                Toast.makeText(this, "Sighting saved", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        })

    }

    // Check if the camera permission is granted
    private fun hasCameraPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST)
    }

    private fun takePhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(cameraIntent)
    }

}