package com.licoding.instagramapp.data.models

import kotlinx.serialization.SerialName
data class Notification(
    val text: String,
    val userId: String,
    @SerialName("created_at")val createdAt: Long
)

