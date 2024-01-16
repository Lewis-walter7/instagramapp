package com.licoding.instagramapp.data.repository

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import com.licoding.instagramapp.data.models.HttpRoutes
import com.licoding.instagramapp.data.remote.client
import com.licoding.instagramapp.data.remote.dto.AuthResponse
import com.licoding.instagramapp.data.remote.dto.AuthenticationResponse
import com.licoding.instagramapp.data.remote.dto.PostResponse
import com.licoding.instagramapp.data.remote.dto.UserResponse
import com.licoding.instagramapp.domain.requests.UserRequest
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.IOException

class UserRepositoryImpl(
    private val sharedPreferences: SharedPreferences
): UserRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun createUser(user: UserRequest): UserResponse {
        return withContext(Dispatchers.IO){
            try {
                val response = client.post {
                    url(HttpRoutes.registerRoute)
                    contentType(ContentType.Application.Json)
                    setBody(user)
                }
                loginUser(user)
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
                    accountType = "",
                    createdAt = 0
                )
            }
        }
    }

        @SuppressLint("CommitPrefEdits")
        override suspend fun loginUser(user: UserRequest): AuthResponse {
            return withContext(Dispatchers.IO){
                try {
                    val response = client.post {
                        url(HttpRoutes.loginRoute)
                        contentType(ContentType.Application.Json)
                        setBody(user)
                    }
                    response.body<AuthResponse>()
                } catch (e: IOException) {
                    println(e.message)
                    return@withContext AuthResponse(
                        token = null
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

    override suspend fun authenticate(): AuthenticationResponse {
        return try {
            val response = client.get {
                val token = sharedPreferences.getString("jwt-token", null)
                url(HttpRoutes.AUTHENTICATEROUTE)
                headers{
                    append("Authorization", "Bearer $token")
                }
            }
            response.body<AuthenticationResponse>()
        } catch (e: Exception) {
            println(e.stackTraceToString())
            AuthenticationResponse(isAuthenticated = false)
        }
    }
    override suspend fun getUserInfo(): UserResponse {
        return withContext(Dispatchers.IO) {
            val token = sharedPreferences.getString("jwt-token", null)
            val response = client.get {
                url(HttpRoutes.USERINFOROUTE)
                headers{
                    append("Authorization", "Bearer $token")
                }
            }
            response.body<UserResponse>()
        }
    }
}