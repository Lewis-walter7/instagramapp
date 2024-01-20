package com.licoding.instagramapp.presentation.upload.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.licoding.instagramapp.data.models.User
import com.licoding.instagramapp.presentation.common.IndeterminateCircularIndicator
import com.licoding.instagramapp.presentation.common.UserPhoto
import com.licoding.instagramapp.presentation.upload.UploadUIEvent
import com.licoding.instagramapp.presentation.upload.UploadUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadScreen(
    state: UploadUIState,
    user: User?,
    onEvent: (UploadUIEvent) -> Unit,
    navController: NavController,
    closeIntent: () -> Unit
) {
    val scope = CoroutineScope(Dispatchers.Default)
    Scaffold(
        topBar = {
            TopAppBar(
                 title = {
                     Text(
                         text = "New Post",
                         fontWeight = FontWeight.SemiBold
                     )
                 },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                            onEvent(UploadUIEvent.ShowAppBar(true))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    Text(
                        text = "Share",
                        fontSize = 15.sp,
                        modifier = Modifier
                            .clickable {
                                scope.launch {
                                    onEvent(UploadUIEvent.OnUploadButtonClicked)
                                    delay(4000)
                                    closeIntent()
                                }
                            }
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .height(70.dp)
                    .padding(7.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(end = 7.dp),
                ) {
                    UserPhoto(45.dp, user!!)
                    Text(
                        text = user.username,
                        fontSize = 10.sp
                    )
                }
                TextField(
                    value = state.caption ?: "",
                    placeholder = {
                        Text(
                            text = "Write a caption or add a poll...",
                            fontSize = 12.sp
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.background),
                    onValueChange = {
                        onEvent(UploadUIEvent.OnCaptionChange(it))
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
                AsyncImage(
                    model = state.selectedUri,
                    contentDescription = null,
                    modifier = Modifier
                        .height(60.dp)
                        .aspectRatio(1f)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = Color.LightGray, thickness = 1.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {
                        onEvent(UploadUIEvent.ShowAppBar(false))
                        navController.navigate("advancedsettings")
                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Advanced settings",
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                )
            }
            IndeterminateCircularIndicator()
        }
    }
}