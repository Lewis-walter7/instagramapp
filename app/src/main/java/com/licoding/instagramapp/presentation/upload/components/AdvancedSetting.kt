package com.licoding.instagramapp.presentation.upload.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.licoding.instagramapp.presentation.upload.UploadUIEvent
import com.licoding.instagramapp.presentation.upload.UploadUIState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvancedSetting(
    navController: NavController,
    onEvent: (UploadUIEvent) -> Unit,
    state: UploadUIState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Advanced settings"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp)
        ) {
            item {
                LikesAndViews(
                    state,
                    onEvent
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Comments(
                    state,
                    onEvent
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Preferences()
            }
        }
    }
}

@Composable
fun LikesAndViews(
    state: UploadUIState,
    onEvent: (UploadUIEvent) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = "Likes and views",
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Hide like and view count on this post",
                modifier = Modifier
                    .weight(1f)
            )
            Switch(
                checked = state.hideLikes,
                onCheckedChange = {
                    onEvent(UploadUIEvent.OnHideLikesChange(it))
                },
                modifier = Modifier
                    .height(10.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Only you will see the total number of likes and view on this post. You can change this later by clicking the three dots on top of the post. To hide the like count on other people's posts, go your account settings. Learn More",
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            fontSize = 12.sp
        )
    }
}

@Composable
fun Comments(
    state: UploadUIState,
    onEvent: (UploadUIEvent) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = "Comments",
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Turn off commenting",
                modifier = Modifier
                    .weight(1f)
            )
            Switch(
                checked = state.turnOffComments,
                onCheckedChange = {
                    onEvent(UploadUIEvent.OnAllowCommentsChange(it))                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "You can change this later by clicking the three dots on top of the post.",
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            fontSize = 12.sp
        )
    }
}

@Composable
fun Preferences(){
    var checked by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = "Preferences",
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Turn off commenting",
                modifier = Modifier
                    .weight(1f)
            )
            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "We're simplifying audience control.",
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            fontSize = 12.sp
        )
    }
}