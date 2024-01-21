package com.licoding.instagramapp.data.remote.user


import com.licoding.instagramapp.domain.requests.FollowRequests
import org.w3c.dom.Comment
import java.util.UUID

interface UserApiService {
    suspend fun likePost(userId: UUID): Boolean
    suspend fun followUser(followRequests: FollowRequests)
    suspend fun commentOnPost(userId: UUID, comment: Comment): Boolean
}