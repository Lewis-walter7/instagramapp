package com.licoding.instagramapp.presentation.upload.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.licoding.instagramapp.data.models.Image
import com.licoding.instagramapp.presentation.upload.UploadUIEvent
import com.licoding.instagramapp.presentation.upload.UploadUIState

@Composable
fun UploadHome(
    images: List<Image>,
    state: UploadUIState,
    onEvent: (UploadUIEvent) -> Unit,
    navController: NavController
) {
    val state1 = rememberLazyListState()

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri -> onEvent(UploadUIEvent.onSelectedUriChange(uri!!))}
    )
    Surface {
        LazyColumn(
            state = state1,
            contentPadding = PaddingValues(5.dp)
        ) {
            item {
                Header()
            }
            item {
                PhotoShow(state = state1)
            }
            item {
                if (state.selectedUri == null) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(5.dp))
                            .border(width = 1.dp, MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(5.dp))
                            .clickable {
                                singlePhotoPicker.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }
                    ) {
                       Column {
                           Icon(
                               imageVector = Icons.Default.PhotoLibrary,
                               contentDescription = null,
                               modifier = Modifier
                                   .size(100.dp),
                               tint = Color.LightGray
                           )
                           Text(
                               text = "Pick Media"
                           )
                       }
                    }
                } else {
                    Column {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp)
                                .border(
                                    width = 1.dp,
                                    MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(5.dp)
                                )
                        ) {
                            AsyncImage(
                                model = state.selectedUri,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(5.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(
                                onClick = {
                                    singlePhotoPicker.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                },
                                content = {
                                    Text(
                                        "Change Media"
                                    )
                                },
                                modifier = Modifier
                                    .padding(5.dp)
                            )
                            TextButton(
                                onClick = {
                                    navController.navigate("upload")
                                },
                                content = {
                                    Text(
                                        "Next"
                                    )
                                },
                                modifier = Modifier
                                    .width(120.dp)
                                    .padding(5.dp)
                            )
                        }
                    }
                }
            }
        }
//        LazyVerticalGrid(
//            columns = GridCells.Adaptive(minSize = 128.dp)
//        ) {
//            items(images){ photo ->
//                AsyncImage(
//                    model = photo,
//                    contentDescription = null
//                )
//            }
//        }
    }
}