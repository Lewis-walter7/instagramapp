package com.licoding.instagramapp.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AssignmentInd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.licoding.instagramapp.R
import com.licoding.instagramapp.data.remote.dto.PostResponse
import com.licoding.instagramapp.presentation.common.UserPhoto
import com.licoding.instagramapp.presentation.main.components.BottomSheet
import com.licoding.instagramapp.presentation.main.components.PostsView
import com.licoding.instagramapp.presentation.main.components.ProfileBottomSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfile(
    state: MainUIState,
    navController: NavController,
    posts: List<PostResponse>,
    onEvent: (MainUIEvent) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    val onDismissRequest = {
        showBottomSheet = false
    }
    val user = state.searchedUser
    val currentUser = state.currentUser
    val follow = if (user?.id  == currentUser?.id) "Edit Profile" else "Follow"
    val message = if (user?.id == currentUser?.id) "Message" else "Share profile"
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = user?.username ?: ""
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            showBottomSheet = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
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
                .padding(top = 70.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = user?.profileImage ?: R.drawable.placeholderavatar,
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(50)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(30.dp))

                    Row(
                        modifier = Modifier
                            .weight(1f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        FollowCountItem(
                            title = "Posts",
                            count = user?.postCount ?: 0
                        )
                        FollowCountItem(
                            title = "Followers",
                            count = user?.followerCount ?: 0
                        )

                        FollowCountItem(
                            title = "Following",
                            count = user?.followingCount ?: 0
                        )
                    }
                }
            }
            item {
                user?.name?.let { it1 ->
                    Text(
                        text = it1,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
            item {
                user?.bio?.let { it1 ->
                    Text(
                        text = it1,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = follow,
                        modifier = Modifier
                            .weight(1f)
                            .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(7.dp))
                            .padding(5.dp)
                            .clickable {
                                onEvent(MainUIEvent.OnFollowButtonClicked)
                            },
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = message,
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
                    ProfileBottomSheet(
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
fun FollowCountItem(
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