package com.licoding.instagramapp.presentation.upload.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.licoding.instagramapp.data.models.User
import com.licoding.instagramapp.domain.room.UserDao
import com.licoding.instagramapp.presentation.common.UserPhoto
import com.licoding.instagramapp.presentation.upload.UploadUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadScreen(
    state: UploadUIState,
    user: User
) {
    var caption by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth()
                .height(100.dp)
        ){
            UserPhoto(size = 100.dp, user = user)
            TextField(
                value = caption,
                placeholder = {
                    Text(
                        text = "Write a caption or add a poll..."
                    )
                },
                modifier = Modifier
                    .weight(1f),
                onValueChange = {
                    caption = it
                }
            )
            AsyncImage(
                model = state.selectedUri,
                contentDescription = null,
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()
            )
        }
    }
}