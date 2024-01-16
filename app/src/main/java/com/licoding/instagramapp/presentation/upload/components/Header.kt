package com.licoding.instagramapp.presentation.upload.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.AddToPhotos
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Header() {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 65.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        HeaderItem(
            icon = Icons.Outlined.PhotoCamera,
            title = "Camera",
            screenWidth = screenWidth
        )
        HeaderItem(
            icon = Icons.Outlined.AddBox,
            title = "Draft",
            screenWidth = screenWidth
        )
        HeaderItem(
            icon = Icons.Outlined.AddToPhotos,
            title = "Templates",
            screenWidth = screenWidth
        )
    }
}
@Composable
fun HeaderItem(
    icon: ImageVector,
    title: String,
    screenWidth: Dp
) {
    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .width(screenWidth / 3.3f)
                .height(100.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(10.dp))
                .padding(7.dp),
            contentAlignment = Alignment.Center
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
                Text(
                    text = title
                )
            }
        }
    }
}