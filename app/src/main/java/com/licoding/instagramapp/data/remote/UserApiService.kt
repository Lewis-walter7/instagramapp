package com.licoding.instagramapp.data.remote


import org.w3c.dom.Comment
import java.util.UUID

interface UserApiService {
    suspend fun likePost(userId: UUID): Boolean
    suspend fun followUser(userId: UUID): Boolean
    suspend fun commentOnPost(userId: UUID, comment: Comment): Boolean
}