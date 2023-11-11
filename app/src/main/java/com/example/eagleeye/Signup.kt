package com.example.eagleeye

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class Signup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        var btn_signup: Button

        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        val loginText = findViewById<TextView>(R.id.loginText)
        loginText.setOnClickListener{
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }

        //Old SQLite DB
        // Creating an object for the database process.
        //val db = DBHelper(this, null)

        //New Firebase DB
        val db = Firebase.firestore

        btn_signup = findViewById(R.id.btnSaveAccount)
        btn_signup.setOnClickListener{

            // Perform validations
            val validation = Validation()
            val invalidFields = mutableListOf<String>()

            //---------------------Input and Validation for First Name----------------------------//

            var txtFirstName = findViewById<EditText>(R.id.editFirstName);
            var firstName = txtFirstName.text.toString() // Storing the FirstName;

            //validating firstname
            if (!validation.validateStringsWithoutNumbers(firstName)) {
                invalidFields.add("Firstname")
                txtFirstName.error = "Only letters allowed!"
            }

            //------------------------------------------------------------------------------------//



            //---------------------Input and Validation for Surname-------------------------------//

            var txtSurname = findViewById<EditText>(R.id.editSurname);
            var surname = txtSurname.text.toString() // Storing the Surname;

            if (!validation.validateStringsWithoutNumbers(surname)) {
                invalidFields.add("Surname")
                txtSurname.error = "Only letters allowed!"
            }

            //------------------------------------------------------------------------------------//



            //---------------------Input and Validation for Userame-------------------------------//

            var txtUsername = findViewById<EditText>(R.id.editUsername);
            var username = txtUsername.text.toString(); // Storing the username

            if (!validation.validateStringsWithNumbers(username) || username.length < 5) {
                invalidFields.add("Username")
                txtUsername.error = "Only letters and numbers are allowed allowed and it needs to be longer than 5 characters"
            }


            //------------------------------------------------------------------------------------//



            //---------------------Input and Validation for Email Address-------------------------//
            var txtEmailAddress = findViewById<EditText>(R.id.editEmailAddress);
            var email = txtEmailAddress.text.toString(); // Storing the email

            if (!validation.validEmail(email)) {
                invalidFields.add("Email Address")
                txtEmailAddress.error = "Must be a valid email address"
            }


            //------------------------------------------------------------------------------------//


            //---------------------Input and Validation for Password------------------------------//
            var txtPassword = findViewById<EditText>(R.id.editPassword);
            var password = txtPassword.text.toString(); // Storing the password

            if (password.length < 7) {
                invalidFields.add("Password")
                txtPassword.error = "Password must be longer than 7 characters"
            }

            //------------------------------------------------------------------------------------//



            //---------------------Input and Validation for Confirm Password ---------------------//
            var txtPasswordConfirmation = findViewById<EditText>(R.id.editPasswordConfirmation);
            var passwordConfiramtion = txtPasswordConfirmation.text.toString(); // Storing the password.

            if (password != passwordConfiramtion) {
                invalidFields.add("confirmPassword")
                txtPasswordConfirmation.error = "Both passwords need to match"
            }

            //------------------------------------------------------------------------------------//


            /*if(password == passwordConfiramtion) {

                // Passing the information into the method which will be stored in the database.
                db.registerUser(firstName, surname, username, email, password);

                // Clearing the text after signing up.
                txtFirstName.setText("");
                txtSurname.setText("");
                txtEmailAddress.setText("");
                txtPassword.setText("");
                txtPasswordConfirmation.setText("");

                *//*val intent = Intent(this@Signup, Login::class.java)
                startActivity(intent)*//*

            } else {
                Toast.makeText(this, "Password must match with password confirmation", Toast.LENGTH_SHORT).show()
            }*/

            //checking for errors and displaying toast message if any are found
            if (invalidFields.isNotEmpty()) {
                val errorMessage = "Invalid input/s. Please check the following field(s): ${
                    invalidFields.joinToString(", ")
                }"
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Stop execution if any field is invalid
            } else{
                //Old SQLite code
                /*db.registerUser(firstName, surname, username, email, password);

                // Clearing the text after signing up.
                txtFirstName.setText("");
                txtSurname.setText("");
                txtEmailAddress.setText("");
                txtPassword.setText("");
                txtPasswordConfirmation.setText("");
                val intent = Intent(this@Signup, Login::class.java)
                startActivity(intent) */

                //New Firebase code
                val user = hashMapOf(
                    "Email" to email,
                    "Firstname" to firstName,
                    "Password" to password,
                    "Surname" to surname,
                    "Username" to username
                )
                db.collection("user")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        val intent = Intent(this@Signup, Login::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            }

        }

    }

}