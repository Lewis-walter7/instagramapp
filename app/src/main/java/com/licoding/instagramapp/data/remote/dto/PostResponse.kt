package com.licoding.instagramapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    val postUrl: String,
    val userId: String,
    val showComments: Boolean? = null,
    val hideLikes: Boolean? = null,
    val createdAt: Long,
    val likes: Long,
    val id: String
)
