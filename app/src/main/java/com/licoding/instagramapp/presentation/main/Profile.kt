package com.licoding.instagramapp.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.AssignmentInd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.licoding.instagramapp.data.models.User
import com.licoding.instagramapp.data.remote.dto.PostResponse
import com.licoding.instagramapp.data.remote.repository.user.UserRepository
import com.licoding.instagramapp.presentation.common.UserPhoto
import com.licoding.instagramapp.presentation.main.components.BottomSheet
import com.licoding.instagramapp.presentation.main.components.PostView
import com.licoding.instagramapp.presentation.main.components.PostsView

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(
    userRepository: UserRepository,
    user: User,
    navController: NavController,
    posts: List<PostResponse>,
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    val onDismissRequest = {
        showBottomSheet = false
    }
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
                            TODO()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddBox,
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            showBottomSheet = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 70.dp, bottom = 10.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserPhoto(
                        size = 70.dp,
                        user = user
                    )

                    Spacer(modifier = Modifier.width(30.dp))

                    Row(
                        modifier = Modifier
                            .weight(1f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CountItem(
                            title = "Posts",
                            count = user.postCount ?: 0
                        )

                        CountItem(
                            title = "Followers",
                            count = user.followerCount ?: 0
                        )

                        CountItem(
                            title = "Following",
                            count = user.followingCount ?: 0
                        )
                    }
                }
            }
            item {
                user.name?.let { it1 ->
                    Text(
                        text = it1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp)
                    )
                }
            }
            item {
                user.bio?.let { it1 ->
                    Text(
                        text = it1,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                ) {
                    Text(
                        text = "Edit Profile",
                        modifier = Modifier
                            .weight(1f)
                            .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(7.dp))
                            .padding(5.dp)
                            .clickable {
                                  navController.navigate("editprofile")
                            },
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = "Share Profile",
                        modifier = Modifier
                            .weight(1f)
                            .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(7.dp))
                            .padding(5.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(
                        onClick = {
                            TODO()
                        },
                        modifier = Modifier
                            .height(25.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(7.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Default.PersonAdd,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            item {
                if (showBottomSheet) {
                    BottomSheet(
                        onDismissRequest = onDismissRequest,
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 20.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            TODO()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.GridOn,
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            TODO()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Slideshow,
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            TODO()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AssignmentInd,
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }
                }
            }
            item {
                PostsView(posts)
            }
        }
    }
}

@Composable
fun CountItem(
    title: String,
    count: Long
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$count",
            fontWeight = FontWeight.Bold
        )
        Text(
            text = title
        )
    }
}