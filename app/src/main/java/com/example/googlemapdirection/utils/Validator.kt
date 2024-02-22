package com.example.googlemapdirection.utils

fun isEmpty(value: String): Boolean {
    return value.trim().isEmpty()
}

fun isValidEmail(email: String): Boolean {
    return email.trim().isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
}

fun isValidPassword(password:String) : Boolean{
    if(password.length < 8 || password.contains(" ")){
        return false
    }
    return true
}