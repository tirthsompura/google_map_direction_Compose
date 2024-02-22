package com.example.googlemapdirection.screens.common

import android.view.Gravity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.example.googlemapdirection.R
import com.example.googlemapdirection.ui.theme.customPrimaryColor
import com.example.googlemapdirection.ui.theme.fontRegular


@Composable
fun ConfirmationDialogBottomSheet(
    title: String,
    label: String,
    icon: Painter,
    positiveButtonText: String = "Accept",
    negativeButtonText: String = "Decline",
    positiveButtonTextStyle: TextStyle = fontRegular.copy(
        color = Color.White, fontSize = 18.sp,
        lineHeight = 24.sp,
    ),
    negativeButtonTextStyle: TextStyle = fontRegular.copy(
        color = Color.Black, fontSize = 18.sp,
        lineHeight = 24.sp,
    ),
    onDismiss: () -> Unit, onAccept: () -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Dialog(
        onDismissRequest = {
            onDismiss()
        }, properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        dialogWindowProvider.window.setGravity(Gravity.BOTTOM)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CommonText(
                        text = title,
                        style = fontRegular.copy(fontSize = 18.sp, lineHeight = 24.sp)
                    )
                    Image(painter = painterResource(id = R.drawable.ic_cross_img_black),
                        contentDescription = "",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = {
                                    onDismiss()
                                }
                            )
                    )
                }
                SpacerVertical(height = 10.dp)
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(shape = CircleShape)
                        .background(customPrimaryColor), contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = icon,
                        contentDescription = "",
                        modifier = Modifier.size(36.dp),
                        colorFilter = ColorFilter.tint(color = Color.White)
                    )
                }

                SpacerVertical(height = 10.dp)
                CommonText(
                    text = label,
                    style = fontRegular.copy(fontSize = 18.sp, lineHeight = 24.sp),
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    maxLine = 2
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (negativeButtonText.isNotEmpty()) {
                            DeleteButtonWithIconTile(
                                style = negativeButtonTextStyle,
                                height = 50.dp,
                                borderRadious = 12.dp,
                                label = negativeButtonText,
                                modifier = Modifier.weight(0.1f), icon = null
                            ) {
                                onDismiss()
                            }
                            SpacerHorizontal(width = 12.dp)
                        }
                        GradientButtonWithIcon1(
                            style = positiveButtonTextStyle,
                            height = 50.dp,
                            icon = null,
                            label = positiveButtonText,
                            iconTint = Color.White,
                            borderRadious = 12.dp,
                            modifier = Modifier.weight(0.1f),
                        ) {
                            onAccept()
                        }
                    }
                }
            }
        }
    }
}