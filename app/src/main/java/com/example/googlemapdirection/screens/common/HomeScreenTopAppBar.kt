package com.example.googlemapdirection.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.googlemapdirection.R
import com.example.googlemapdirection.ui.theme.fontRegular
import com.example.googlemapdirection.ui.theme.tertiaryColor
import com.example.googlemapdirection.utils.FirebaseKeyConstants.USER
import com.example.googlemapdirection.utils.SessionManagerClass

@Composable
fun HomeScreenTopAppBar(
    session: SessionManagerClass,
    logoutButton: () -> Unit,
) {
    TopAppBar(
        elevation = 0.dp, backgroundColor = tertiaryColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Hey ${session.loginUserData?.firstName}," + " You are a " + if (session.loginUserData?.userType == USER) "user and please place the ride." else "employee and please accept the ride.",
                style = fontRegular.copy(
                    fontSize = 16.sp,
                    color = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.9f)
            )

            Image(
                painter = painterResource(id = R.drawable.power_off),
                contentDescription = "",
                modifier = Modifier
                    .clickable(
                        onClick = {
                            logoutButton()
                        }
                    )
                    .fillMaxWidth()
                    .weight(0.2f)
                    .size(25.dp),
                colorFilter = ColorFilter.tint(Color.White),
            )
        }
    }
}