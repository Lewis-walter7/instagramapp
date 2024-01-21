package com.licoding.instagramapp.data.remote.repository.post

import com.licoding.instagramapp.data.models.HttpRoutes
import com.licoding.instagramapp.data.remote.client
import com.licoding.instagramapp.data.remote.dto.PostResponse
import com.licoding.instagramapp.domain.requests.PostRequest
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.setBody
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class PostRepositoryImpl: PostRepository {
    override suspend fun createPost(postRequest: PostRequest): PostResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.post {
                    url(HttpRoutes.UPLOAD)
                    contentType(ContentType.Application.Json)
                    setBody(postRequest)
                }
                response.body<PostResponse>()
            } catch (e: IOException) {
                return@withContext PostResponse(
                    postUrl = "",
                    userId= "",
                    showComments = null,
                    hideLikes = null,
                    createdAt = 0,
                    likes = 0,
                    id = ""
                )
            }
        }
    }
    override suspend fun deletePost() {
        TODO("Not yet implemented")
    }
}