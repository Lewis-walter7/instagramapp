package com.licoding.instagramapp.presentation.register.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameScreen(navController: NavController) {
    var username by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Choose username",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 30.sp
            ),
            textAlign = TextAlign.Center
        )

        Text(
            text = "You can always change it later.",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 15.sp
            ),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp)),
            label = {
                Text(
                    text = "Username",
                    color = Color.Gray,
                )
            },
            keyboardOptions = KeyboardOptions.Default.also {
                ImeAction.Next
            },
            value = username,
            onValueChange = {
                username = it
            }
        )

        OutlinedButton(
            onClick = {
                navController.navigate("passwordscreen")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            enabled = username.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (username.isNotEmpty()) Color.Blue else Color.Gray
            ),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(
                text = "Next",
                color = Color.White
            )
        }
    }
}

@Composable
fun PasswordScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ){
        Text(
            text = "Login",
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun Register() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ){
        Text(
            text = "Login",
            modifier = Modifier.fillMaxWidth()
        )
    }
}