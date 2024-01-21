package com.licoding.instagramapp.data.remote.repository.post

import com.licoding.instagramapp.data.remote.dto.PostResponse
import com.licoding.instagramapp.domain.requests.PostRequest

interface PostRepository {
    suspend fun createPost(postRequest: PostRequest): PostResponse
    suspend fun deletePost()
}