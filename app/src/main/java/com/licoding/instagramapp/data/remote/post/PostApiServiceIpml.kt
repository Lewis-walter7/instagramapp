package com.licoding.instagramapp.data.remote.post

import android.content.SharedPreferences
import com.licoding.instagramapp.data.models.HttpRoutes
import com.licoding.instagramapp.data.remote.client
import com.licoding.instagramapp.data.remote.dto.PostResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow

class PostApiServiceIpml(
    private val sharedPreferences: SharedPreferences
): PostApiService {
    override suspend fun getPostByUser(): List<PostResponse> {
        val token = sharedPreferences.getString("jwt-token", null)
        val response = client.get {
            url(HttpRoutes.GETPOSTBYUSER)
            headers {
                append("Authorization", "Bearer $token")
            }
        }
        return response.body()
    }

    override suspend fun getPostProfile(id: String): Flow<PostResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getExplorePosts(): List<PostResponse> {
        val response = client.get {
            url(HttpRoutes.EXPLORE)
        }
        return response.body()
    }
}