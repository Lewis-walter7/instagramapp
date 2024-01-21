package com.licoding.instagramapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserResult(
    @PrimaryKey
    val id: String,
    val username: String,
    val profileImage: String? = null,
    val name: String? = null
)