package com.licoding.instagramapp.data.remote.post

import com.licoding.instagramapp.data.remote.dto.PostResponse
import kotlinx.coroutines.flow.Flow

interface PostApiService {
    suspend fun getPostByUser(): List<PostResponse>
    suspend fun getPostProfile(id: String): Flow<PostResponse>
    suspend fun getExplorePosts(): List<PostResponse>
}