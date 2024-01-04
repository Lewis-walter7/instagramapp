package com.licoding.instagramapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Media(
    val type: MediaType,
    val url: String
)


@Serializable
data class Post(
    val caption: String? = "",
    val media: Media,
    val likes: List<String>?,
    val comments: List<String>?,
    @SerialName("created_at")val createdAt: Long
)
