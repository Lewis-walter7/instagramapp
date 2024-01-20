package com.licoding.instagramapp.presentation.main.components

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.licoding.instagramapp.R
import com.licoding.instagramapp.data.models.User
import com.licoding.instagramapp.presentation.main.MainUIEvent
import com.licoding.instagramapp.presentation.main.MainUIState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditProfile(
    navController: NavController,
    user: User,
    state: MainUIState,
    onEvent: (MainUIEvent) -> Unit
) {
    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri -> onEvent(MainUIEvent.OnSelectedUriChange(uri!!))}
    )

    var username by remember {
        mutableStateOf(user.username)
    }
    var name by remember {
        mutableStateOf(user.name)
    }
    var bio by remember {
        mutableStateOf(user.bio)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text= "Edit profile"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(MainUIEvent.OnUpdateButtonClicked)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {

        val model = if(state.selectedUri != null) state.selectedUri else user.profileImage ?: R.drawable.placeholderavatar
        Column(
            modifier = Modifier
                .padding(top = 70.dp, start = 10.dp, end = 10.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = model,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(50))
                            .aspectRatio(1f)
                            .clickable {
                                singlePhotoPicker.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    Text(
                        text = "Edit picture or avatar",
                        color = Color.Magenta,
                        modifier = Modifier
                            .clickable {
                                singlePhotoPicker.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }
                    )
                }
            }
            TextField(
                value = name ?: "",
                onValueChange = {
                    name = it
                    onEvent(MainUIEvent.OnNameChange(name.toString()))
                },
                placeholder = {
                    Text(
                        text = "Name"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    KeyboardCapitalization.Words
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = username,
                onValueChange = {
                    username = it
                    onEvent(MainUIEvent.OnUsernameChange(username.toString()))
                },
                placeholder = {
                    Text(
                        text = "Username"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = bio ?: "",
                onValueChange = {
                    bio = it
                    onEvent(MainUIEvent.OnBioChange(bio.toString()))
                },
                placeholder = {
                    Text(
                        text = "Bio"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}