package com.licoding.instagramapp.data.remote.dto

import com.licoding.instagramapp.data.models.AccountType
import com.licoding.instagramapp.domain.services.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

data class UserResponse(
    val id: String,
    val username: String,
    val password: String,
    val email: String? = "",
    val phoneNumber: String? = "",
    val bio: String? = "",
    val profileImage: String?,
    val accountType: AccountType?,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime?
)
