package com.licoding.instagramapp.presentation.register.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.licoding.instagramapp.R
import com.licoding.instagramapp.domain.requests.UserRequest
import com.licoding.instagramapp.presentation.register.RegisterUIEvent
import com.licoding.instagramapp.presentation.register.RegisterUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    navController: NavController,
    newUser: UserRequest?,
    state: RegisterUIState,
    onEvent: (RegisterUIEvent) -> Unit
) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Gray)){
            append("Forgot your login details? ")
        }
        withStyle(style = SpanStyle(color = Color.White)) {
            append("Get help logging in.")
            addStringAnnotation(
                tag = "ClickableText",
                annotation = "ClickAction",
                start = 27,
                end = 47
            )
        }
    }

    val registerAnnotatedString =  buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Gray)){
            append("Don't have an account? ")
        }
        withStyle(style = SpanStyle(color = Color.White)) {
            append("Sign up.")
            addStringAnnotation(
                tag = "ClickableText",
                annotation = "ClickAction",
                start = 23,
                end = 30
            )
        }
    }

    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val newuserRequest = UserRequest(
        username = username,
        password = password
    )
    var showpassword by remember {
        mutableStateOf(false)
    }
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "English (United States)",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 10.dp)
            )
            Column(
                modifier = Modifier.weight(1f)
                    .padding(horizontal = 5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = if(isSystemInDarkTheme()) {
                        painterResource(id = R.drawable.instagramwhitelogo)
                    } else {
                        painterResource(id = R.drawable.instagramblacklogo)
                    },
                    contentDescription = "Instagram Logo",
                    modifier = Modifier.fillMaxWidth()
                        .height(60.dp)

                )
                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                        onEvent(RegisterUIEvent.OnLoginUserNameChange(username))
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    label = {
                        Text(
                            text = "Phone number, email or username",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                        .padding(8.dp)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        onEvent(RegisterUIEvent.OnLoginPasswordChange(password))
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    label = {
                        Text(
                            text = "Password",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                        .padding(7.dp),
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
                OutlinedButton(
                    onClick = {
                        onEvent(RegisterUIEvent.OnLoginButtonClicked(newuserRequest))
                    },
                    enabled = username.isNotEmpty() && password.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp),
                ) {
                    Text(
                        text = "Log in"
                    )
                }
                ClickableText(
                    text = annotatedString,
                    onClick = { offset ->
                        val annotation = annotatedString.getStringAnnotations("ClickableText", offset, offset)
                        if(annotation.isNotEmpty()) {
                            navController.navigate("forgotDetails")
                        }
                    }
                )
            }
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            ClickableText(
                text = registerAnnotatedString,
                onClick = { offset ->
                    val annotation = annotatedString.getStringAnnotations("ClickableText", offset, offset)
                    if(annotation.isNotEmpty()) {
                        navController.navigate("register")
                    }
                },
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
        }
    }
}