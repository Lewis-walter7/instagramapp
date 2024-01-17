package com.licoding.instagramapp.domain.requests

import kotlinx.serialization.Serializable

@Serializable
data class PostRequest(
    val postUrl: String,
    val userId: String,
    val showComments: Boolean,
    val hideLikes: Boolean,
    val caption: String? = null
)
