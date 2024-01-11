package com.licoding.instagramapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String? = null,
    val message: String? = null
)
