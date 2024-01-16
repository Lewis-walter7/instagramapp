package com.licoding.instagramapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationResponse(
    val isAuthenticated: Boolean
)