package com.licoding.instagramapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.InstagramappTheme
import com.licoding.instagramapp.presentation.register.RegisterUIEvent
import com.licoding.instagramapp.presentation.register.RegisterUIState
import com.licoding.instagramapp.presentation.register.components.ForgotDetails
import com.licoding.instagramapp.presentation.register.components.Login
import com.licoding.instagramapp.presentation.register.components.PasswordScreen
import com.licoding.instagramapp.presentation.register.components.UsernameScreen
import com.licoding.instagramapp.presentation.register.RegisterViewModel

class RegisterActivity: ComponentActivity() {
    private val viewModel by viewModels<RegisterViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        setContent {
            val state = viewModel.state.collectAsState()
            InstagramappTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MyApp(
                        state = state,
                        onEvent = viewModel::onEvent
                    )
                }
            }
        }
    }
}

@Composable
fun MyApp(
    state: State<RegisterUIState>,
    onEvent: (RegisterUIEvent) -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            Login(
                navController = navController,
                state = state,
                onEvent = onEvent
            )
        }
        composable("forgotDetails") {
            ForgotDetails(navController = navController)
        }
        composable("usernamescreen") {
            UsernameScreen(navController)
        }
        composable("passwordscreen") {
            PasswordScreen()
        }
    }
}

@Preview
@Composable
fun SplashScreen(){
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center
            ){
               Image(
                   painter = painterResource(id = R.drawable.logo),
                   contentDescription = null,
                   modifier = Modifier.weight(1f)
                       .fillMaxWidth()
                       .height(40.dp),
                   alignment = Alignment.Center,


               )
                Text(
                    text = "from",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Image(
                    painter = painterResource(id = R.drawable.metalogo),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    alignment = Alignment.Center
                )
            }
        }
    }
}

