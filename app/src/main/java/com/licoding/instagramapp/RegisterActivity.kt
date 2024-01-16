package com.licoding.instagramapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.compose.InstagramappTheme
import com.licoding.instagramapp.data.remote.NetworkObserver
import com.licoding.instagramapp.data.repository.UserRepositoryImpl
import com.licoding.instagramapp.domain.room.InstagramDatabase
import com.licoding.instagramapp.domain.services.NetworkObserverImpl
import com.licoding.instagramapp.presentation.register.components.ForgotDetails
import com.licoding.instagramapp.presentation.register.components.Login
import com.licoding.instagramapp.presentation.register.components.PasswordScreen
import com.licoding.instagramapp.presentation.register.components.UsernameScreen
import com.licoding.instagramapp.presentation.register.RegisterViewModel

class RegisterActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val db = Room.databaseBuilder(
            this,
            InstagramDatabase::class.java,
            "instagram.db"
        ).build()

        val sharedPreferences = getSharedPreferences("instagramPref", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt-token", null)
        val viewModel by viewModels<RegisterViewModel>(
            factoryProducer = {
                object :ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return RegisterViewModel(
                            userRepository = UserRepositoryImpl(sharedPreferences = sharedPreferences),
                            sharedPreferences = sharedPreferences,
                            userDao = db.userDao,
                            connectivityManager = NetworkObserverImpl(applicationContext)
                        ) as T
                    }
                }
            }
        )

        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        setContent {
            val state by viewModel.state.collectAsState()
            val newUser = viewModel.newUser
            val status by viewModel.networkStatus.collectAsState()

            LaunchedEffect(viewModel) {
                viewModel.responseChannel.collect { value ->
                    if(value) {
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(intent)
                    } //else if(token != null && status != NetworkObserver.NetworkStatus.Available) {
                       // val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        //startActivity(intent)
                    //}
                }
            }
            InstagramappTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            Login(
                                navController = navController,
                                state = state,
                                newUser = newUser,
                                onEvent = viewModel::onEvent
                            )
                        }
                        composable("forgotDetails") {
                            ForgotDetails(navController = navController)
                        }
                        navigation(startDestination = "usernamescreen", route = "register"){
                            composable("usernamescreen") {
                                UsernameScreen(
                                    navController = navController,
                                    onEvent = viewModel::onEvent,
                                    sharedPreferences = sharedPreferences
                                )
                            }
                            composable("passwordscreen") {
                                PasswordScreen(
                                    state = state,
                                    navController = navController,
                                    onEvent = viewModel::onEvent,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


