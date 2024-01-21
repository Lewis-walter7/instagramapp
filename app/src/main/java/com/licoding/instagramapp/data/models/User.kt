package com.licoding.instagramapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val id: String,
    val username: String,
    val password: String,
    val email: String? = null,
    val phoneNumber: String? = null,
    val bio: String? = null,
    val profileImage: String?,
    val accountType: String,
    val createdAt: Long?,
    val name: String? = null,
    val followerCount: Long? = 0,
    val followingCount: Long? = 0,
    val postCount: Long? =0
)