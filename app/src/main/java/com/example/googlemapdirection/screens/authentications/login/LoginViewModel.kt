package com.example.googlemapdirection.screens.authentications.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.googlemapdirection.utils.SessionManagerClass
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val sessionManagerClass: SessionManagerClass,
) : ViewModel() {
    val emailAddress: MutableState<String> = mutableStateOf("")
    val emailErrMsg: MutableState<String> = mutableStateOf("")
    val password: MutableState<String> = mutableStateOf("")
    val passwordErrMsg: MutableState<String> = mutableStateOf("")
}