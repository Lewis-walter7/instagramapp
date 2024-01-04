package com.licoding.instagramapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String? = "",
    val username: String,
    val password: String,
    val email: String? = null,
    @SerialName("phone_number")val phoneNumber: String? = null,
    val bio: String? = null,
    val followers: List<String>,
    @SerialName("profile_image")val profileImage: String?,
    @SerialName("account_type")val accountType: AccountType,
    @SerialName("created_at")val createdAt: Long
)