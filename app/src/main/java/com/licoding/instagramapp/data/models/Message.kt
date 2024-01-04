package com.licoding.instagramapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val text: String,
    val senderId: String,
    val resourceUrl: Media,
    val receiverId: String,
    @SerialName("created_at")val createdAt: Long
)
