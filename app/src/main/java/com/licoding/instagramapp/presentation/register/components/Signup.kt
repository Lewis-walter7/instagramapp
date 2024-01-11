package com.licoding.instagramapp.presentation.register.components


import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.licoding.instagramapp.presentation.register.RegisterUIEvent
import com.licoding.instagramapp.presentation.register.RegisterUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameScreen(
    navController: NavController,
    onEvent: (RegisterUIEvent) -> Unit,
    sharedPreferences: SharedPreferences
) {
    var username by remember {
        mutableStateOf("")
    }
    val editor = sharedPreferences.edit()

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
                onEvent(RegisterUIEvent.OnLoginUserNameChange(it))
            }
        )

        TextButton(
            onClick = {
                editor.apply {
                    putString("username", username)
                    apply()
                }
                navController.navigate("passwordscreen")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            enabled = username.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (username.isNotEmpty()) MaterialTheme.colorScheme.onTertiary else Color.Gray
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordScreen(
    state: RegisterUIState,
    navController: NavController,
    onEvent: (RegisterUIEvent) -> Unit,
) {
    var password by remember {
        mutableStateOf("")
    }
    var showpassword by remember {
        mutableStateOf(false)
    }
    val focusRequester = remember { FocusRequester() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Create a password",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 30.sp
            ),
            textAlign = TextAlign.Center
        )

        Text(
            text = "For security, your password must be 6 \n characters or more",
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
                .clip(RoundedCornerShape(5.dp))
                .focusRequester(focusRequester),
            label = {
                Text(
                    text = "Password",
                    color = Color.Gray,
                )
            },
            keyboardOptions = KeyboardOptions.Default.also {
                ImeAction.Done
            },
            value = password,
            onValueChange = {
                password = it
                onEvent(RegisterUIEvent.OnLoginPasswordChange(it))
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        showpassword = !showpassword
                    }
                ) {
                    if (showpassword) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = null
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                }
            },
            visualTransformation = if (showpassword) VisualTransformation.None else PasswordVisualTransformation()
        )

        TextButton(
            onClick = {
                 onEvent(RegisterUIEvent.OnRegisterButtonClicked)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            enabled = password.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (password.isNotEmpty()) MaterialTheme.colorScheme.onTertiary else Color.Gray
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
