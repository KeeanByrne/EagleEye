package com.example.eagleeye

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class Signup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        var btn_signup: Button

        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        // Creating an object for the database process.
        val db = DBHelper(this, null)

        btn_signup = findViewById(R.id.btnSaveAccount);
        btn_signup.setOnClickListener{
            var txtFirstName = findViewById<EditText>(R.id.editFirstName);
            var firstName = txtFirstName.text.toString() // Storing the firstName;

            var txtSurname = findViewById<EditText>(R.id.editSurname);
            var surname = txtSurname.text.toString() // Storing the firstName;

            var txtUsername = findViewById<EditText>(R.id.editUsername);
            var username = txtUsername.text.toString(); // Storing the username

            var txtEmailAddress = findViewById<EditText>(R.id.editEmailAddress);
            var email = txtEmailAddress.text.toString(); // Storing the email

            var txtPassword = findViewById<EditText>(R.id.editPassword);
            var password = txtPassword.text.toString(); // Storing the password

            var txtPasswordConfirmation = findViewById<EditText>(R.id.editPasswordConfirmation);
            var passwordConfiramtion = txtPasswordConfirmation.text.toString(); // Storing the password.

            if(password == passwordConfiramtion) {

                // Passing the information into the method which will be stored in the database.
                db.registerUser(firstName, surname, username, email, password);

                // Clearing the text after signing up.
                txtFirstName.setText("");
                txtSurname.setText("");
                txtEmailAddress.setText("");
                txtPassword.setText("");
                txtPasswordConfirmation.setText("");
            }

        }

    }

}