package com.licoding.instagramapp.presentation.main.search

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.licoding.instagramapp.R
import com.licoding.instagramapp.presentation.main.MainUIEvent
import com.licoding.instagramapp.presentation.main.MainUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchResult(
    navController: NavController,
    state: MainUIState,
    onEvent: (MainUIEvent) -> Unit
) {
    val scope = CoroutineScope((Dispatchers.Main))

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = state.searchUsername ?: "",
                        placeholder = {
                            Text(
                                text= "Search"
                            )
                        },
                        onValueChange = {
                            onEvent(MainUIEvent.OnSearchUsernameChange(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null
                            )
                        }
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
                },
                actions = {
                    Spacer(modifier = Modifier.width(16.dp))
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 70.dp, start = 10.dp, end = 10.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Recent",
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "See all",
                        color = Color.Blue
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(state.searchUsers){user ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clickable {
                            scope.launch {
                                onEvent(MainUIEvent.OnUserResultClick(user.id))
                                delay(300)
                                navController.navigate("profile/${user.id}")
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = user.profileImage ?: R.drawable.placeholderavatar,
                        contentDescription = null,
                        modifier = Modifier
                            .size(65.dp)
                            .clip(RoundedCornerShape(50)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = user.username,
                            fontSize = 18.sp
                        )
                        user.name?.let { it1 ->
                            Text(
                                text = it1,
                                fontSize = 15.sp,
                                color = Color.LightGray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(
                        onClick = {
                            onEvent(MainUIEvent.OnDeleteUserResult)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            modifier = Modifier
                                .size(13.dp)
                        )
                    }
                }
            }
        }
    }
}