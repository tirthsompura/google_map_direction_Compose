package com.example.googlemapdirection.screens.authentications.login

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.googlemapdirection.response.UserResponse
import com.example.googlemapdirection.utils.FirebaseKeyConstants
import com.example.googlemapdirection.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

fun loginAuth(
    context: Context,
    auth: FirebaseAuth,
    viewModel: LoginViewModel,
    onSignedIn: (FirebaseUser) -> Unit,
) {
    auth.signInWithEmailAndPassword(viewModel.emailAddress.value, viewModel.password.value)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val db = FirebaseFirestore.getInstance()
                db.collection(FirebaseKeyConstants.Users)
                    .whereEqualTo(FirebaseKeyConstants.Email, "${user?.email}")
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            val dataSessionData: UserResponse = viewModel.sessionManagerClass.loginUserData ?: UserResponse()
                            dataSessionData.email = document.data.getValue(FirebaseKeyConstants.Email) as String
                            dataSessionData.firstName =
                                document.data.getValue(FirebaseKeyConstants.FirstName) as String
                            dataSessionData.lastName = document.data.getValue(FirebaseKeyConstants.LastName) as String
                            dataSessionData.userId = document.id
                            dataSessionData.userType = document.data.getValue(FirebaseKeyConstants.UserType) as String
                            viewModel.sessionManagerClass.loginUserData = dataSessionData
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.e(TAG, "Error getting documents: ", exception)
                    }
                onSignedIn(user!!)
            } else {
                showToast(context, "Please create an account fist.")
            }
        }
}