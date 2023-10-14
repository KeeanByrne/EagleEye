package com.example.eagleeye

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

// eBirdAPIHelper Class/
class eBirdAPIHelper {

    private val eBirdApiToken = "8vvustaqj75r" // eBird 2.0 API Token.
    private val client = OkHttpClient()
    val regionCode = "KZ" // Replace with the desired region code

    // Testing the API connection.
    fun testAPIConnection(): String? {
        val url = "https://api.ebird.org/v2/data/obs/$regionCode/recent"
        val request = Request.Builder()
            .url(url)
            .header("X-eBirdApiToken", eBirdApiToken)
            .build()
        var request_response = ""

        try {
            val response: Response = client.newCall(request).execute()

            // Check the response status
            if (response.isSuccessful) {
                // The connection was successful
                // You can print a message or perform other actions here
                request_response = "Connection to eBird API successful"
            } else {
                // The connection was not successful
                // You can print an error message or handle the failure
                request_response = "Connection to eBird API failed"
            }
        } catch (e: Exception) {
            // Handle any exceptions, such as network errors
            e.printStackTrace()
        }

        return request_response // returning the response

    }

    // Getting the Nearby Hotspots based on the longitude and latitude.
    /*
    fun getNearbyHotspot(latitude: String, longitude: String): String? {

    } */

}
