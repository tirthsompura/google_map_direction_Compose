package com.example.googlemapdirection.screens.authentications.login

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
import com.example.googlemapdirection.R
import com.example.googlemapdirection.screens.common.CommonText
import com.example.googlemapdirection.screens.common.GradientButton
import com.example.googlemapdirection.screens.common.OutlinedSimpleTextFiled
import com.example.googlemapdirection.screens.common.SpacerVertical
import com.example.googlemapdirection.ui.theme.Purple80
import com.example.googlemapdirection.ui.theme.fontRegular
import com.example.googlemapdirection.ui.theme.lightGrey
import com.example.googlemapdirection.utils.AuthFirebase
import com.example.googlemapdirection.utils.RootGraph
import com.example.googlemapdirection.utils.ValidationConstants
import com.example.googlemapdirection.utils.isEmpty
import com.example.googlemapdirection.utils.isValidEmail
import com.example.googlemapdirection.utils.isValidPassword

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val auth = AuthFirebase.auth

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
                text = stringResource(R.string.login_your_account),
                style = TextStyle(fontSize = 20.sp),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )
            SpacerVertical(20.dp)
            OutlinedSimpleTextFiled(
                name = loginViewModel.emailAddress,
                placeHolder = stringResource(R.string.email),
                isPasswordField = false,
                horizontal = 0.dp,
                modifier = Modifier.fillMaxWidth(),
                errorMsg = loginViewModel.emailErrMsg.value
            ) {

            }
            SpacerVertical(10.dp)
            OutlinedSimpleTextFiled(
                name = loginViewModel.password,
                placeHolder = stringResource(R.string.password),
                isPasswordField = true,
                horizontal = 0.dp,
                modifier = Modifier.fillMaxWidth(),
                errorMsg = loginViewModel.passwordErrMsg.value
            ) {

            }
            SpacerVertical(30.dp)

            GradientButton(
                style = fontRegular.copy(
                    fontSize = 18.sp,
                    lineHeight = 24.sp,
                    color = Color.White
                ),
                label = stringResource(R.string.login),
                modifier = Modifier.fillMaxWidth()
            ) {
                //Validation with Login Success.
                validationForLogin(loginViewModel) {
                    loginAuth(
                        context, auth, loginViewModel
                    ) { signedInUser ->
                        navigateToHomePage(navController)
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
                    text = stringResource(R.string.don_t_have_an_account),
                    style = TextStyle(fontSize = 15.sp, color = lightGrey),
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                )
                CommonText(
                    text = stringResource(R.string.signup),
                    style = TextStyle(fontSize = 15.sp, color = Purple80),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            navController.navigate(AuthenticationScreens.RegisterScreen.route)
                        }
                    )
                )
            }
        }
    }
}

fun validationForLogin(signViewModel: LoginViewModel, onSuccess: () -> Unit) {
    if (isEmpty(signViewModel.emailAddress.value)) {
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

fun navigateToHomePage(navController: NavController) {
    navController.navigate(RootGraph.DASHBOARD) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }
}