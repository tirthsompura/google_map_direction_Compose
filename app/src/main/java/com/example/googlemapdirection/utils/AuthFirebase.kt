package com.example.googlemapdirection.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object AuthFirebase {
    val auth: FirebaseAuth by lazy { Firebase.auth }
}