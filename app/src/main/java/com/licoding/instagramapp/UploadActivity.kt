package com.licoding.instagramapp

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.compose.InstagramappTheme
import com.licoding.instagramapp.data.models.Image
import com.licoding.instagramapp.data.remote.FirebaseService
import com.licoding.instagramapp.data.repository.post.PostRepositoryImpl
import com.licoding.instagramapp.domain.room.InstagramDatabase
import com.licoding.instagramapp.domain.services.PhotoContProvider
import com.licoding.instagramapp.presentation.upload.UploadViewModel
import com.licoding.instagramapp.presentation.upload.components.AdvancedSetting
import com.licoding.instagramapp.presentation.upload.components.UploadHome
import com.licoding.instagramapp.presentation.upload.components.UploadScreen

class UploadActivity: ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.READ_MEDIA_IMAGES,
                    android.Manifest.permission.READ_MEDIA_VIDEO
                ),
                0
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                ),
                0
            )
        }

        val images = PhotoContProvider(this)

        super.onCreate(savedInstanceState)
        val closeIntent = {
            startActivity(Intent(this@UploadActivity, MainActivity::class.java))
            finish()
        }
        setContent {
            val viewmodel by viewModels<UploadViewModel>(
                factoryProducer = {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return UploadViewModel(application, PostRepositoryImpl()) as T
                        }
                    }
                }
            )
            viewmodel.updateImages(images)
            val state by viewmodel.state.collectAsState()

            val navController = rememberNavController()
            InstagramappTheme {
                Surface {
                    Scaffold(
                        topBar = {
                            if (state.showAppBar) {
                                CenterAlignedTopAppBar(
                                    title = {
                                        Text(
                                            text = "New reel"
                                        )
                                    },
                                    navigationIcon = {
                                        IconButton(onClick = {
                                            startActivity(Intent(this@UploadActivity, MainActivity::class.java))
                                            finish()
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = null
                                            )
                                        }
                                    },
                                    actions = {
                                        IconButton(
                                            onClick = {
                                                TODO()
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Settings,
                                                contentDescription = null
                                            )
                                        }
                                    },
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.background)
                                )
                            }
                        }
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = "start"
                        ) {
                            composable("start") {
                                UploadHome(
                                    state = state,
                                    onEvent = viewmodel::onEvent,
                                    navController = navController
                                )
                            }
                            composable("upload") {
                                UploadScreen(
                                    state = state,
                                    user = viewmodel.user!!,
                                    onEvent = viewmodel::onEvent,
                                    navController = navController,
                                    closeIntent = closeIntent
                                )
                            }
                            composable("advancedsettings"){
                                AdvancedSetting(
                                    navController = navController,
                                    onEvent = viewmodel::onEvent,
                                    state = state
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}