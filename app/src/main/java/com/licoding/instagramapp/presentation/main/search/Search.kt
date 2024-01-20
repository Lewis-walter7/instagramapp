package com.licoding.instagramapp.presentation.main.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.licoding.instagramapp.data.remote.dto.PostResponse

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Search(
    posts: List<PostResponse>,
    navController: NavController
) {
    var value by remember {
        mutableStateOf("")
    }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val screenwidth = LocalConfiguration.current.screenWidthDp.dp
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    TextField(
                        value = "",
                        placeholder = {
                            Text(
                                text= "Search"
                            )
                        },
                        onValueChange = {
                            value = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { focusState ->
                                if (focusState.isFocused) {
                                    navController.navigate("searchResult")
                                }
                            },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null
                            )
                        }
                    )
                },
                actions = {
                    Spacer(modifier = Modifier.width(16.dp))
                }
            )
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.FixedSize(screenwidth / 3f),
            modifier = Modifier
                .padding(top = 70.dp)
        ) {
            items(posts) {post ->
                AsyncImage(
                    model = post.postUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .height(140.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}