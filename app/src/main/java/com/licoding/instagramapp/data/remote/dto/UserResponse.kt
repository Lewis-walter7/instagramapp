package com.licoding.instagramapp.data.remote.dto

import com.licoding.instagramapp.data.models.AccountType
import com.licoding.instagramapp.domain.services.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class UserResponse(
    val id: String,
    val username: String,
    val password: String,
    val profileImage: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val bio: String? = null,
    val createdAt: Long?,
    val accountType: String? = null
)
