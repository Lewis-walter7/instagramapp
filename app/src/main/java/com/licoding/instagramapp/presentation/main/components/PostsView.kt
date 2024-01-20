package com.licoding.instagramapp.presentation.main.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.licoding.instagramapp.data.remote.dto.PostResponse

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PostsView(posts: List<PostResponse>) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    if (posts.isEmpty()) {
        Column {
            Text(
                text = "No posts"
            )
        }
    } else {
//        LazyVerticalGrid(
//            columns = GridCells.FixedSize(screenWidth / 3),
//            verticalArrangement = Arrangement.spacedBy(4.dp),
//            horizontalArrangement = Arrangement.spacedBy(4.dp)
//        ) {
//            items(posts) { post ->
//                PostView(post)
//            }
//        }
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            maxItemsInEachRow = 3,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            posts.forEach { post ->
                PostView(post)
            }
        }
    }
}

@Composable
fun PostView(post: PostResponse) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    AsyncImage(
        model = post.postUrl,
        contentDescription = null,
        modifier = Modifier.width(screenWidth / 3.2f)
            .height(140.dp),
        contentScale = ContentScale.FillBounds,
    )
}
