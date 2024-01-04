package com.licoding.instagramapp.data.remote.dto

import com.licoding.instagramapp.data.models.Media
import com.licoding.instagramapp.domain.services.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

data class PostResponse(
    val caption: String? = "",
    val media: Media,
    val likes: List<String>?,
    val comments: List<String>?,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime
)
