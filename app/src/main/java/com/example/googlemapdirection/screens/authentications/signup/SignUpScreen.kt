package com.example.googlemapdirection.screens.authentications.signup

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.googlemapdirection.navigations.AuthenticationScreens
import com.example.googlemapdirection.response.UserResponse
import com.example.googlemapdirection.R
import com.example.googlemapdirection.screens.common.CommonText
import com.example.googlemapdirection.screens.common.GradientButton
import com.example.googlemapdirection.screens.common.OutlinedSimpleTextFiled
import com.example.googlemapdirection.screens.common.SpacerVertical
import com.example.googlemapdirection.ui.theme.Purple80
import com.example.googlemapdirection.ui.theme.fontRegular
import com.example.googlemapdirection.ui.theme.lightGrey
import com.example.googlemapdirection.utils.AuthFirebase
import com.example.googlemapdirection.utils.FirebaseKeyConstants.FirstName
import com.example.googlemapdirection.utils.FirebaseKeyConstants.LastName
import com.example.googlemapdirection.utils.FirebaseKeyConstants.UserType
import com.example.googlemapdirection.utils.FirebaseKeyConstants.Users
import com.example.googlemapdirection.utils.ValidationConstants
import com.example.googlemapdirection.utils.isEmpty
import com.example.googlemapdirection.utils.isValidEmail
import com.example.googlemapdirection.utils.isValidPassword
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(
    userData: MutableState<FirebaseUser?>,
    navController: NavController,
    signViewModel: SignUpViewModel = hiltViewModel(),
) {
    val auth = AuthFirebase.auth
    val userLoginData = remember { mutableStateOf<UserResponse?>(null) }

    LaunchedEffect(userData.value?.uid) {
        val fireStore = FirebaseFirestore.getInstance()
        val userDocRef = fireStore.collection(Users).document("${userData.value?.uid}")
        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val firstName = document.getString(FirstName)
                    val lastName = document.getString(LastName)
                    val userType = document.getString(UserType)
                    userLoginData.value = UserResponse(
                        firstName = firstName ?: "",
                        lastName = lastName ?: "",
                        email = userData.value?.email ?: "",
                        userType = userType ?: ""
                    )
                } else {
                    // Handle the case where the document doesn't exist
                }
            }
            .addOnFailureListener { e ->
                // Handle failure
            }
    }


    val context = LocalContext.current
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            SpacerVertical(20.dp)
            CommonText(
                text = stringResource(R.string.hey_there),
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier
                    .fillMaxWidth()
            )
            CommonText(
                text = stringResource(R.string.create_an_account),
                style = TextStyle(fontSize = 20.sp),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )
            SpacerVertical(20.dp)
            OutlinedSimpleTextFiled(
                name = signViewModel.firstName,
                placeHolder = stringResource(R.string.first_name),
                isPasswordField = false,
                horizontal = 0.dp,
                modifier = Modifier.fillMaxWidth(),
                errorMsg = signViewModel.firstNameErrMsg.value
            ) {

            }
            SpacerVertical(10.dp)
            OutlinedSimpleTextFiled(
                name = signViewModel.lastName,
                placeHolder = stringResource(R.string.last_name),
                isPasswordField = false,
                horizontal = 0.dp,
                modifier = Modifier.fillMaxWidth(),
                errorMsg = signViewModel.lastNameErrMsg.value
            ) {

            }
            SpacerVertical(10.dp)
            OutlinedSimpleTextFiled(
                name = signViewModel.userType,
                placeHolder = stringResource(R.string.please_enter_user_type_user_employee),
                isPasswordField = false,
                horizontal = 0.dp,
                modifier = Modifier.fillMaxWidth(),
//                errorMsg = signViewModel.userTypeErrMsg.value
            ) {

            }
            SpacerVertical(10.dp)
            OutlinedSimpleTextFiled(
                name = signViewModel.emailAddress,
                placeHolder = stringResource(R.string.email),
                isPasswordField = false,
                horizontal = 0.dp,
                modifier = Modifier.fillMaxWidth(),
                errorMsg = signViewModel.emailErrMsg.value
            ) {

            }
            SpacerVertical(10.dp)
            OutlinedSimpleTextFiled(
                name = signViewModel.password,
                placeHolder = stringResource(R.string.password),
                isPasswordField = true,
                horizontal = 0.dp,
                modifier = Modifier.fillMaxWidth(),
                errorMsg = signViewModel.passwordErrMsg.value
            ) {

            }
            SpacerVertical(30.dp)

            GradientButton(
                style = fontRegular.copy(
                    fontSize = 18.sp,
                    lineHeight = 24.sp,
                    color = Color.White
                ),
                label = stringResource(R.string.sign_up),
                modifier = Modifier.fillMaxWidth()
            ) {
                validationForSignUp(signViewModel) {
                    signUpAuth(context, auth, signViewModel) { signedInUser ->
                        navController.navigate(AuthenticationScreens.LoginScreen.route)
                        Toast.makeText(context, "SignUp Success", Toast.LENGTH_LONG).show()
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                CommonText(
                    text = stringResource(R.string.already_have_an_account),
                    style = TextStyle(fontSize = 15.sp, color = lightGrey),
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                )
                CommonText(
                    text = stringResource(R.string.login),
                    style = TextStyle(fontSize = 15.sp, color = Purple80),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            navController.navigate(AuthenticationScreens.LoginScreen.route)
                        }
                    )
                )
            }
        }
    }
}

fun validationForSignUp(signViewModel: SignUpViewModel, onSuccess: () -> Unit) {
    if (isEmpty(signViewModel.firstName.value)) {
        signViewModel.firstNameErrMsg.value = ValidationConstants.FirstNameBlank
    } else if (isEmpty(signViewModel.lastName.value)) {
        signViewModel.lastNameErrMsg.value = ValidationConstants.LastNameBlank
    }
    else if (isEmpty(signViewModel.emailAddress.value)) {
        signViewModel.emailErrMsg.value = ValidationConstants.EmailBlank
    } else if (!isValidEmail(signViewModel.emailAddress.value)) {
        signViewModel.emailErrMsg.value = ValidationConstants.EmailValid
    } else if (isEmpty(signViewModel.password.value)) {
        signViewModel.emailErrMsg.value = ""
        signViewModel.passwordErrMsg.value = ValidationConstants.PasswordBlank
    } else if (!isValidPassword(signViewModel.password.value)) {
        signViewModel.emailErrMsg.value = ""
        signViewModel.passwordErrMsg.value = ValidationConstants.PasswordValid
    } else {
        signViewModel.passwordErrMsg.value = ""
        onSuccess()
    }
}
