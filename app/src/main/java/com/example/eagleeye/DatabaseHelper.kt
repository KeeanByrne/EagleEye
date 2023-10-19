package com.example.eagleeye

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.text.AlphabeticIndex.Record
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Database Helper is the file that will be used for making database
// queries and creating the appropriate database tables.

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {

        // below is a sqlite query, where column names
        // along with their data types is given
        val user_create_query = ("CREATE TABLE " + User_Table + " ("
                + "Id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Firstname" + " VARCHAR," +
                "Surname" + " VARCHAR," +
                "Username" + " VARCHAR," +
                "Email" + " VARCHAR," +
                "Password" + " VARCHAR" +
                ")")

        // Table to create the settings option for each user.
        val settings_create_query = ("CREATE TABLE " + Settings_Table + " (" +
                "Id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "UserID" + " INTEGER, " +
                "RouteColour" + " VARCHAR, " +
                "AppMainColour" + " VARCHAR, " +
                "DistanceMinimum" + " INTEGER, " +
                "DistanceMaximum" + " INTEGER, " +
                "FOREIGN KEY(UserID) REFERENCES " + User_Table + "(Id)" + // Define the foreign key constraint
                ")")

        // Table to have the hotspot location.
        val hotspot_create_query = ("CREATE TABLE " + Hotspot_Table + " (" +
                "Id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name" + " VARCHAR, " +
                "Lattitude" + " VARCHAR, " +
                "Longitude" + " VARCHAR, " +
                "Country " + "VARCHAR " +
                ")")

        // Table to have the species
        val species_create_query = ("CREATE TABLE " + Species_Table + " (" +
                "Id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name" + " VARCHAR, " +
                "ScientificName" + " VARCHAR, " +
                "Family" + " VARCHAR " +
                ")")

        // Table to have the records.
        val record_create_query = ("CREATE TABLE " + Record_Table + " (" +
                "Id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "UserID " + " INTEGER, " +
                "HotspotID" + " INTEGER, " +
                "SpeciesID" + " INTEGER, " +
                "ObservationDate DATETIME, " +
                "Notes TEXT, " +
                "Numbers INTEGER, " +
                "FOREIGN KEY(UserID) REFERENCES " + User_Table + "(Id), " + // Define the foreign key constraint
                "FOREIGN KEY(HotspotID) REFERENCES " + Hotspot_Table + "(Id), " + // Define the foreign key constraint
                "FOREIGN KEY(SpeciesID) REFERENCES " + Species_Table + "(Id) " + // Define the foreign key constraint
                ")")

        // Table to store sightings.
        val sighting_create_query = ("CREATE TABLE " + Sighting_Table + " (" +
                "Id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "BirdName" + " VARCHAR, " +
                "LatinName" + " VARCHAR, " +
                "Location" + " VARCHAR, " +
                "BirdPhotoBlob" + " BLOB, " +
                "Timestamp" + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")"
                )


        // Table to store the species observed.

        // we are calling sqlite
        // method for executing our query
        db.execSQL(user_create_query)  // Creating the User Table.
        db.execSQL(settings_create_query) // Creating the Settings Table.
        db.execSQL(hotspot_create_query) // Creating the Hotspot Table.
        db.execSQL(species_create_query) // Creating the Species Table.
        db.execSQL(record_create_query) // Creating the record Table.
        db.execSQL(sighting_create_query) // Creating the sighting Table.



    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        // Dropping the Tables if they already exist.
        db.execSQL("DROP TABLE IF EXISTS " + Settings_Table)
        db.execSQL("DROP TABLE IF EXISTS " + Record_Table)
        db.execSQL("DROP TABLE IF EXISTS " + User_Table)
        db.execSQL("DROP TABLE IF EXISTS " + Hotspot_Table)
        db.execSQL("DROP TABLE IF EXISTS " + Species_Table)
        db.execSQL("DROP TABLE IF EXISTS " + Sighting_Table)

        onCreate(db)
    }

    // Registering the user.
    fun registerUser(firstName: String, surname: String, username: String,
                     emailAddress: String, password: String) {

        // Creating a content values variable

        val values = ContentValues();

        // Inserting the values from the registration form
        values.put("Firstname", firstName)
        values.put("Surname", surname)
        values.put("Username", username)
        values.put("Email", emailAddress)

        // Encrypting the password first before putting password into database
        // Salt and hash the password
        val pwdProcess = SaltHashPassword();
        var passwordConfig = pwdProcess.hashPassword(password)
        values.put("Password", passwordConfig)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // Inserting the details into the user table.
        db.insert(User_Table, null, values)

    }

    // Signing the user in
    @SuppressLint("Range")
    fun checkUser(usernameCheck: String, passwordCheck: String): Boolean {
        // Creating a readable database instead of a writable database
        val db = this.readableDatabase

        val pwdProcess = SaltHashPassword(); // Creating an object to use the Salt and Hash Password class.
        var validUser = false

        // below code returns a cursor to
        // read data from the database
        val rowExists = "SELECT Email, Password FROM " + User_Table + " WHERE Username = ?"
        val selectionArgs = arrayOf(usernameCheck)
        val cursor = db.rawQuery(rowExists, selectionArgs)

        val enteredPasswordHash = pwdProcess.hashPassword(passwordCheck)

        if(cursor.moveToFirst()) {
            var databaseHashPassword = cursor.getString(cursor.getColumnIndex("Password"));

            if(enteredPasswordHash == databaseHashPassword) {
                validUser = true
            }
        }

        return validUser
    }

    //Allowing the user to update their settings
    @SuppressLint("Range")
    fun updateSettings(userID: Int, )
    {

    }

    // Function to return the ID of the user.
    @SuppressLint("Range")
    fun returnID(username: String): String {
        val db = this.readableDatabase
        var capturedID = ""

        // Creating an object for the database process.

        // below code returns a cursor to
        // read data from the database
        val rowExists = "SELECT Id FROM " + User_Table  + " WHERE Username = ?"
        val selectionArgs = arrayOf(username)
        val cursor = db.rawQuery(rowExists, selectionArgs)

        if(cursor.moveToFirst()) {
            capturedID = cursor.getString(cursor.getColumnIndex("Id")).toString();
        }

        return capturedID
    }
    companion object {

        // Variable for the database name
        private val DATABASE_NAME = "EagleEye"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variables for table names
        val User_Table = "User"
        val Settings_Table = "Settings"
        val Hotspot_Table = "Hotspot"
        val Species_Table = "Species"
        val Record_Table = "Record"
        val Sighting_Table = "Sighting"


    }


    //-------Method to add a new bird sighting to the database-------//
    fun addSighting(birdName: String, latinName: String, location: String) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put("BirdName", birdName)
        values.put("LatinName", latinName)
        values.put("Location", location)

        //Method for getting the current timestamp as a string
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        values.put("Timestamp", timestamp)

        db.insert(Sighting_Table, null, values)
        db.close()
    }

    fun addSightingWithImage(birdName: String, latinName: String, location: String, birdPhoto: ByteArray?) {
        val db = this.writableDatabase

        db.beginTransaction()

        try {
            val values = ContentValues()
            values.put("BirdName", birdName)
            values.put("LatinName", latinName)
            values.put("Location", location)
            values.put("BirdPhotoBlob", birdPhoto)

            val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            values.put("Timestamp", timestamp)

            //Inserting the data into the database
            val result = db.insert(Sighting_Table, null, values)

            //--------------------------Debugging code--------------------------//
            if (result != -1L) {
                Log.d("AddSightingWithImage", "Image saved successfully")
            } else {
                Log.e("AddSightingWithImage", "Image not saved to the database")
            }
            //--------------------------Debugging code--------------------------//


            //Setting the transaction as successful
            db.setTransactionSuccessful()
        } finally {
            //Ending the transaction
            db.endTransaction()
            db.close()
        }
    }

}