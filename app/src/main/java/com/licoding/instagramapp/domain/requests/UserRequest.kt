package com.licoding.instagramapp.domain.requests

import androidx.compose.runtime.MutableState
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val username: String,
    val password: String
)