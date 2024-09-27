package com.example.eagleeye


//-------------------------Class to handle all User Input for Registration------------------------//
class Validation {

    // String validation where numbers/letters/white spaces are allowed
    fun validateStringsWithNumbers(string: String): Boolean {
        val pattern = Regex("^[a-zA-Z0-9\\s.]*$")
        return pattern.matches(string)
    }

    // String Validation where only letters/white spaces are allowed
    fun validateStringsWithoutNumbers(string:String):Boolean{
        val pattern = Regex("^[a-zA-Z\\s]+$")
        return pattern.matches(string)
    }

    //Email Address validation
    fun validEmail(email: String): Boolean {
        val pattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        return email.matches(Regex(pattern))
    }
}

//-------------------------------------------End of File------------------------------------------//