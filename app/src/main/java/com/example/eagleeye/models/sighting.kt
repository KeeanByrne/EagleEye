package com.example.eagleeye.models

data class Sighting(
    val BirdName: String = "",
    val LatinName: String = "",
    val Location: String = "",
    val Image: String? = null,  // You can store the image URL here
    val UserID: String = ""
)
