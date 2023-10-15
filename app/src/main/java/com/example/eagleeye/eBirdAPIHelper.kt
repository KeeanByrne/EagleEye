package com.example.eagleeye


import android.widget.Toast
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

// eBirdAPIHelper Class/
class eBirdAPIHelper {

    private val eBirdApiToken = "8vvustaqj75r" // eBird 2.0 API Token.
    private val client = OkHttpClient()
    val regionCode = "KZ" // Replace with the desired region code

    // Testing the API connection.
    fun testAPIConnection() {
        val url = "https://api.ebird.org/v2/data/obs/$regionCode/recent"
        val request = Request.Builder()
            .url(url)
            .header("X-eBirdApiToken", eBirdApiToken)
            .build()

        val response: Response = client.newCall(request).execute()

        if (response.isSuccessful) {
            println("Connection to eBird API successful")
        } else {
            println("Connection to eBird API failed with status code: ${response.code}")
        }
    }

    // Getting the Nearby Hotspots based on the longitude and latitude.

    //-33.8084826,18.4763215

    fun getNearbyHotspot(latitude: String, longitude: String): List<HotspotData> {

        // Test amount of hotspots
        val url = "https://api.ebird.org/v2/ref/hotspot/geo?lat=$latitude&lng=$longitude"

        val request = Request.Builder()
            .url(url)
            .header("X-eBirdApiToken", eBirdApiToken)
            .build()

        val response: Response = client.newCall(request).execute()
        var hotspotDataList = mutableListOf<HotspotData>()


        if (response.isSuccessful) {

            println("Connection to eBird API successful")

            // Formatting the data.
            var rawData = response.body?.string()

            // Checking if the data is not null.
            if(rawData != null) {
                // Split the data into individual lines
                val lines = rawData.split("\n")

                // Process each line and parse it into a HotspotData object
                for (line in lines) {
                    val fields = line.split(",")
                    if (fields.size >= 8) {
                        val id = fields[0]
                        val country = fields[1]
                        val region = fields[2]
                        val latitude = fields[4].toDouble()
                        val longitude = fields[5].toDouble()
                        val name = fields[6]

                        // Create a HotspotData object and add it to the list
                        hotspotDataList.add(
                            HotspotData(id, country, region, latitude, longitude, name)
                        )
                    }
                }
            }
        } else {
            println("Connection to eBird API failed with status code: ")
            return emptyList()

        }

        return hotspotDataList

    }

    data class HotspotData(
        val id: String,
        val country: String,
        val region: String,
        val latitude: Double,
        val longitude: Double,
        val name: String,
    )

}





