package com.example.googlemapdirection.nointernet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.googlemapdirection.screens.common.CommonText
import com.example.googlemapdirection.screens.common.GradientButton
import com.example.googlemapdirection.screens.common.SpacerVertical
import com.example.googlemapdirection.ui.theme.fontRegular

@Composable
fun NoInternetUIDialog(dismissDialog: () -> Unit={}) {

    val isBottomSheetVisible = remember { mutableStateOf(true) }

    if(isBottomSheetVisible.value){
            Dialog(onDismissRequest =  dismissDialog ) {
                Surface(
                    modifier = Modifier.height(400.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    NoInternetUIScreen(){}
                }
            }
    }
}
@Composable
fun NoInternetUIScreen(verticalPadding: Dp = 0.dp, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = verticalPadding, horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommonText(text = "No internet connection", style = fontRegular.copy(fontSize = 24.sp), modifier = Modifier )
        SpacerVertical(height = 5.dp)
        CommonText(
            text = "Please check your internet connection or try again",
            style = fontRegular.copy(fontSize = 16.sp, color = Color.DarkGray),
            textAlign = TextAlign.Center,
            maxLine = 2
        )
        SpacerVertical(height = 20.dp)
        GradientButton(
            style = fontRegular.copy(
                fontSize = 18.sp,
                lineHeight = 24.sp,
                color = Color.White
            ),
            label = "Try Again",
            modifier = Modifier.fillMaxWidth()
        ) {
            onClick()
        }
    }
}