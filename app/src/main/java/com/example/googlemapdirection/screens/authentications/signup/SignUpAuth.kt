package com.example.googlemapdirection.screens.authentications.signup

import android.content.Context
import com.example.googlemapdirection.R
import com.example.googlemapdirection.utils.FirebaseKeyConstants
import com.example.googlemapdirection.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

fun signUpAuth(
    context: Context,
    auth: FirebaseAuth,
    viewModel: SignUpViewModel,
    onSignedIn: (FirebaseUser) -> Unit,
) {
    auth.createUserWithEmailAndPassword(viewModel.emailAddress.value, viewModel.password.value)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                // Create a user profile in FireStore
                val userProfile = hashMapOf(
                    FirebaseKeyConstants.FirstName to viewModel.firstName.value,
                    FirebaseKeyConstants.LastName to viewModel.lastName.value,
                    FirebaseKeyConstants.UserType to viewModel.userType.value,
                    FirebaseKeyConstants.Email to viewModel.emailAddress.value,
                    FirebaseKeyConstants.Latitude to "",
                    FirebaseKeyConstants.Longitude to "",
                )
                val fireStore = FirebaseFirestore.getInstance()
                fireStore.collection(FirebaseKeyConstants.Users)
                    .document(user!!.uid)
                    .set(userProfile)
                    .addOnSuccessListener {
                        val keyFirstName = userProfile.keys.elementAt(0)
                        val valueOfFirstName = userProfile.getValue(keyFirstName)
                        val keyLastName = userProfile.keys.elementAt(1)
                        val valueOfLastName = userProfile.getValue(keyLastName)
                        val keyUserType = userProfile.keys.elementAt(2)
                        val valueOfUserType = userProfile.getValue(keyUserType)

                        val data = viewModel.sessionManagerClass.loginUserData
                        data?.email = user.email.toString()
                        data?.firstName = (valueOfFirstName)
                        data?.lastName = (valueOfLastName)
                        data?.userId = user.uid
                        data?.userType = (valueOfUserType)
                        viewModel.sessionManagerClass.loginUserData = data
                        onSignedIn(user)
                    }
                    .addOnFailureListener {
                        //handle exception
                        it.printStackTrace()
                    }
            }else {
                // Handle sign-up failure
                showToast(context, context.getString(R.string.failed_to_create_an_account))
            }
        }
}