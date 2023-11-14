package com.example.eagleeye

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog

class LogoutUtil(private val context: Context) {

    fun showLogoutConfirmationDialog(onLogoutConfirmed: () -> Unit) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Logout Confirmation")
        alertDialogBuilder.setMessage("Are you sure you want to logout?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            onLogoutConfirmed.invoke() // Invoke the callback when the user clicks "Yes"
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss() // Dismiss the dialog when the user clicks "No"
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    fun logout() {
        // Clear user data, session, or credentials as needed
        // For example, you can clear shared preferences or reset variables

        // Navigate the user to the login screen
        val intent = Intent(context, Login::class.java)
        context.startActivity(intent)

        // Finish the current activity to prevent the user from returning to it
    }
}
