package com.example.googlemapdirection.screens.common

import android.os.Build
import android.view.Gravity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.googlemapdirection.R
import com.example.googlemapdirection.ui.theme.Purple40
import com.example.googlemapdirection.ui.theme.fontRegular


@Composable
fun ConfirmationDialogBottomSheet(
    userName: MutableState<String>,
    address: String,
    title: String,
    positiveButtonText: String = "Accept",
    negativeButtonText: String = "Reject",
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
    val context = LocalContext.current

    Dialog(
        onDismissRequest = {
            onDismiss()
        }, properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        dialogWindowProvider.window.setGravity(Gravity.CENTER)
        Card(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .height(370.dp),
            shape = RoundedCornerShape(20.dp),
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(75.dp)
                        .clip(shape = CircleShape)
                        .background(Purple40), contentAlignment = Alignment.Center
                ) {
                    val imageLoader = ImageLoader.Builder(context)
                        .components {
                            if (Build.VERSION.SDK_INT >= 28) {
                                add(ImageDecoderDecoder.Factory())
                            } else {
                                add(GifDecoder.Factory())
                            }
                        }
                        .build()
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(context).data(data = R.drawable.ic_car_gif)
                                .apply(block = {
                                    size(150)
                                }).build(),
                            imageLoader = imageLoader
                        ),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                SpacerVertical(height = 10.dp)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                SpacerVertical(height = 10.dp)
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.LightGray))
                SpacerVertical(height = 10.dp)
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                    Text(
                        text = "Name: ",
                        style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Normal,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Start,
                        color = Color.Gray,
                        modifier = Modifier.fillMaxWidth().weight(0.3f)
                    )
                    Text(
                        text = userName.value,
                        style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth().weight(0.8f)
                    )
                }

                SpacerVertical(height = 10.dp)
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Start) {
                    Text(
                        text = "Address: ",
                        style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Normal,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Start,
                        color = Color.Gray,
                        modifier = Modifier.fillMaxWidth().weight(0.3f)
                    )
                    Text(
                        text = address,
                        style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth().weight(0.8f)
                    )
                }

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
