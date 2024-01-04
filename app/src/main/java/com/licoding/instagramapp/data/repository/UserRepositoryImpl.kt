package com.licoding.instagramapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.licoding.instagramapp.data.models.AccountType
import com.licoding.instagramapp.data.models.HttpRoutes
import com.licoding.instagramapp.data.models.User
import com.licoding.instagramapp.data.remote.dto.PostResponse
import com.licoding.instagramapp.data.remote.dto.UserResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.IOException
import java.time.LocalDateTime

class UserRepositoryImpl(
    private val client: HttpClient
): UserRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(InternalAPI::class)
    override suspend fun createUser(user: User): UserResponse {
        return withContext(Dispatchers.IO){
            try {
                val response = client.post {
                    url(HttpRoutes.loginRoute)
                    contentType(ContentType.Application.Json)
                    body = user
                }
                response.body<UserResponse>()
            } catch (e: IOException) {
                println("Failed")
                return@withContext UserResponse(
                    username = "",
                    password = "",
                    profileImage = "",
                    email = "",
                    bio = "",
                    id = "",
                    phoneNumber = "",
                    accountType = AccountType.PUBLIC,
                    createdAt = LocalDateTime.now()
                )
            }
        }
    }

    override fun getUsers(): Flow<List<UserResponse>> {
        TODO("Not yet implemented")
    }

    override fun getUserPosts(): Flow<List<PostResponse>> {
        TODO("Not yet implemented")
    }
}