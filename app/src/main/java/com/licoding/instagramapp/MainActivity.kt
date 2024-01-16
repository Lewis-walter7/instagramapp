package com.licoding.instagramapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.InstagramappTheme
import com.licoding.instagramapp.data.models.BottomNavigatioItem
import com.licoding.instagramapp.presentation.main.*

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this@MainActivity, UploadActivity::class.java)
        setContent {
            val items = listOf(
                BottomNavigatioItem(
                    icon = Icons.Outlined.Home,
                    route = "home",
                ),
                BottomNavigatioItem(
                    icon = Icons.Default.Search,
                    route = "search",
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
            InstagramappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
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
                    ) {
                        NavHost(
                            navController,
                            startDestination = "home"
                        ) {
                            composable("home") {
                                Home()
                            }
                            composable("search") {
                                Search()
                            }
                            composable("reels") {
                                Reels()
                            }
                            composable("profile") {
                                Profile()
                            }
                        }
                    }
                }
            }
        }
    }
}

