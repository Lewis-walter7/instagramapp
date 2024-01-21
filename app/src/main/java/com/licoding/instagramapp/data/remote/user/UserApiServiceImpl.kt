package com.licoding.instagramapp.data.remote.user

import com.licoding.instagramapp.data.models.HttpRoutes
import com.licoding.instagramapp.data.remote.client
import com.licoding.instagramapp.domain.requests.FollowRequests
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.w3c.dom.Comment
import java.util.*

object UserApiServiceImpl: UserApiService {
    override suspend fun likePost(userId: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun followUser(followRequests: FollowRequests) {
         client.post {
             url(HttpRoutes.FOLLOWREQUEST)
             contentType(ContentType.Application.Json)
             setBody(followRequests)
        }
    }

    override suspend fun commentOnPost(userId: UUID, comment: Comment): Boolean {
        TODO("Not yet implemented")
    }
}