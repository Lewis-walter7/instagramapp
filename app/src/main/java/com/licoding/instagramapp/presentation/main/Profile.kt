package com.licoding.instagramapp.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.licoding.instagramapp.data.models.User
import com.licoding.instagramapp.data.repository.user.UserRepository
import com.licoding.instagramapp.presentation.common.UserPhoto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(
    userRepository: UserRepository,
    user: User
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = user.username
                    )
                },
                actions = {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddBox,
                            contentDescription = null
                        )
                    }

                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 70.dp, start = 7.dp, end = 7.dp, bottom = 7.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        UserPhoto(
                            size = 120.dp,
                            user = user
                        )
                        Text(
                            text = user.username
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))

                    Row(
                        modifier = Modifier
                            .weight(1f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CountItem(
                            title = "Posts",
                            count = 2
                        )

                        CountItem(
                            title = "Follwers",
                            count = 87
                        )

                        CountItem(
                            title = "Following",
                            count = 39
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun CountItem(
    title: String,
    count: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$count"
        )
        Text(
            text = title
        )
    }
}