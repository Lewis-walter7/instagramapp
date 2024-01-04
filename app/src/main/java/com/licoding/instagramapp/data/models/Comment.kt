package com.licoding.instagramapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Comment (
    val text: String,
    val userId: String,
    @SerialName("created_at")val createdAt: Long
)