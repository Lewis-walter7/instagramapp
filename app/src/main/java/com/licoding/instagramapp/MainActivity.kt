package com.licoding.instagramapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Slideshow
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.compose.InstagramappTheme
import com.licoding.instagramapp.data.models.BottomNavigatioItem
import com.licoding.instagramapp.data.remote.repository.user.UserRepositoryImpl
import com.licoding.instagramapp.presentation.main.*
import com.licoding.instagramapp.presentation.main.components.EditProfile
import com.licoding.instagramapp.presentation.main.search.Search
import com.licoding.instagramapp.presentation.main.search.SearchResult


class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = getSharedPreferences("instagramPref", Context.MODE_PRIVATE)
        val userRepository = UserRepositoryImpl(sharedPreferences)

        val token = sharedPreferences.getString("jwt-token", null)
        super.onCreate(savedInstanceState)
        val intent = Intent(this@MainActivity, UploadActivity::class.java)
        setContent {
            val viewModel by viewModels<MainViewModel>(
                factoryProducer = {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return MainViewModel(application, userRepository, sharedPreferences) as T
                        }
                    }
                }
            )
            LaunchedEffect(token) {
                if (token == null) {
                    startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
                    finish()
                }
            }
            val items = listOf(
                BottomNavigatioItem(
                    icon = Icons.Outlined.Home,
                    route = "home",
                ),
                BottomNavigatioItem(
                    icon = Icons.Default.Search,
                    route = "explore",
                ),
                BottomNavigatioItem(
                    icon = Icons.Default.AddBox,
                ),
                BottomNavigatioItem(
                    icon = Icons.Default.Slideshow,
                    route = "reels"
                ),
                BottomNavigatioItem(
                    icon = Icons.Default.Home,
                    route = "profile"
                )
            )
            var selectedIndex by remember {
                mutableIntStateOf(0)
            }
            val state by viewModel.state.collectAsState()
            InstagramappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            if (state.showBottomBar) {
                                BottomAppBar(
                                    modifier = Modifier
                                        .height(60.dp)
                                ) {
                                    items.forEachIndexed { index, item ->
                                        NavigationBarItem(
                                            icon = {
                                                Icon(
                                                    imageVector = item.icon,
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .size(30.dp)
                                                )
                                            },
                                            onClick = {
                                                selectedIndex = index
                                                if (item.route != null) {
                                                    navController.navigate(item.route)
                                                } else {
                                                    startActivity(intent)
                                                }
                                            },
                                            selected = index == selectedIndex
                                        )
                                    }
                                }
                            }
                        }
                    ) {
                        NavHost(
                            navController,
                            startDestination = "home"
                        ) {
                            composable("home") {
                                Home()
                            }
                            navigation(startDestination = "search", route = "explore") {
                                composable("search") {
                                    Search(
                                        posts = viewModel.searchPosts,
                                        navController = navController
                                    )
                                }
                                composable("searchResult") {
                                    SearchResult(
                                        navController = navController,
                                        state = state,
                                        onEvent = viewModel::onEvent
                                    )
                                }
                            }
                            composable("reels") {
                                Reels()
                            }
                            composable("profile") {
                                state.currentUser?.let { it1 ->
                                    Profile(
                                        userRepository = userRepository,
                                        user = it1,
                                        navController = navController,
                                        posts = viewModel.posts
                                    )
                                }
                            }
                            composable("editprofile") {
                                state.currentUser?.let { it1 ->
                                    EditProfile(
                                        navController = navController,
                                        user = it1,
                                        state = state,
                                        onEvent = viewModel::onEvent
                                    )
                                }
                            }
                            composable("profile/{userId}") {backStackEntry ->
                                UserProfile(
                                    navController = navController,
                                    state = state,
                                    onEvent = viewModel::onEvent,
                                    posts = viewModel.posts
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


