package com.example.googlemapdirection.screens.authentications.signup

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.googlemapdirection.utils.SessionManagerClass
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val sessionManagerClass: SessionManagerClass,
) : ViewModel() {
    val firstName: MutableState<String> = mutableStateOf("")
    val firstNameErrMsg: MutableState<String> = mutableStateOf("")
    val lastName: MutableState<String> = mutableStateOf("")
    val lastNameErrMsg: MutableState<String> = mutableStateOf("")
    val emailAddress: MutableState<String> = mutableStateOf("")
    val emailErrMsg: MutableState<String> = mutableStateOf("")
    val password: MutableState<String> = mutableStateOf("")
    val passwordErrMsg: MutableState<String> = mutableStateOf("")
    val userType: MutableState<String> = mutableStateOf("")
}